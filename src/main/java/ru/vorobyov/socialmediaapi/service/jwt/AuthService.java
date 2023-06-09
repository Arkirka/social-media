package ru.vorobyov.socialmediaapi.service.jwt;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vorobyov.socialmediaapi.dto.authorization.*;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.model.JwtAuthentication;
import ru.vorobyov.socialmediaapi.service.database.RefreshTokenService;
import ru.vorobyov.socialmediaapi.service.database.UserService;

/**
 * The type Auth service for operation related to authentication.
 */
@Service
public class AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    /**
     * Instantiates a new Auth service.
     *
     * @param userService         the user service
     * @param jwtProvider         the jwt provider
     * @param refreshTokenService the refresh token service
     */
    public AuthService(UserService userService, JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Accepts a DTO request with data for authorization and, if successful, returns a DTO with generated tokens.
     *
     * @param authRequest DTO request with data for authorization
     * @return DTO with access and refresh tokens
     * @throws AuthException throws if user not found or password does not match
     */
    public LoginResponse login(@NonNull LoginRequest authRequest) throws AuthException {
        final User user = userService.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));

        boolean isPasswordMatches = matchHashes(authRequest.getPassword(), user.getPassword());

        if (isPasswordMatches) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user.getId());
            return new LoginResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    /**
     * Accepts a DTO request with data for registration and, if successful, returns true.
     *
     * @param request DTO request with data for registration
     * @return true if registration is successful and false otherwise
     */
    public boolean register(@NonNull RegisterRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(hash(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return userService.add(user).isPresent();
    }

    /**
     * Gets new access token by refresh token and accessToken.
     *
     * @param refreshToken the refresh token
     * @param accessToken  the access token (may be expired)
     * @return new access token
     * @throws AuthException throws if user of refresh token not found
     */
    public NewAccessTokenResponse getAccessToken(@NonNull String refreshToken,
                                                 @NonNull String accessToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getAccessClaims(accessToken);
            final String email = claims.getSubject();
            final User user = userService.findByEmail(email)
                    .orElseThrow(() -> new AuthException("Пользователь не найден"));
            var refreshTokenOptional = refreshTokenService.findByToken(refreshToken);
            if (refreshTokenOptional.isEmpty() || !refreshTokenOptional.get().getToken().equals(refreshToken))
                throw new AuthException("Токен не найден");
            final String newAccessToken = jwtProvider.generateAccessToken(user);
            return new NewAccessTokenResponse(newAccessToken);
        }
        return new NewAccessTokenResponse(null);
    }

    /**
     * Gets new refresh token by old refresh token.
     *
     * @param oldRefreshToken old refresh token
     * @return new refresh token
     */
    public NewRefreshTokenResponse getNewRefreshToken(@NonNull String oldRefreshToken) {
        var user = refreshTokenService.findByToken(oldRefreshToken);
        if (user.isEmpty())
            return null;
        return new NewRefreshTokenResponse(jwtProvider.generateRefreshToken(user.get().getId()));
    }

    /**
     * Gets info of authorized user.
     *
     * @return authorized user info model
     */
    public JwtAuthentication getAuthInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthentication) {
            return (JwtAuthentication) auth;
        } else {
            return null;
        }
    }

    private String hash(String hashableLine) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        return encoder.encode(hashableLine);
    }

    private boolean matchHashes(String line, String hash) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        return encoder.matches(line, hash);
    }

}
