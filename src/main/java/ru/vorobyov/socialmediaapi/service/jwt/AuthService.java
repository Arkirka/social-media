package ru.vorobyov.socialmediaapi.service.jwt;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vorobyov.socialmediaapi.dto.authorization.LoginRequest;
import ru.vorobyov.socialmediaapi.dto.authorization.LoginResponse;
import ru.vorobyov.socialmediaapi.dto.authorization.RegisterRequest;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.model.JwtAuthentication;
import ru.vorobyov.socialmediaapi.service.database.RefreshTokenService;
import ru.vorobyov.socialmediaapi.service.database.UserService;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserService userService, JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

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

    public boolean register(@NonNull RegisterRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(hash(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return userService.add(user).isPresent();
    }

    public LoginResponse getAccessToken(@NonNull String refreshToken, @NonNull String accessToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getAccessClaims(accessToken);
            final String email = claims.getSubject();
            final User user = userService.findByEmail(email)
                    .orElseThrow(() -> new AuthException("Пользователь не найден"));
            var refreshTokenOptional = refreshTokenService.findByToken(refreshToken);
            if (refreshTokenOptional.isEmpty() || !refreshTokenOptional.get().getToken().equals(refreshToken))
                throw new AuthException("Токен не найден");
            final String newAccessToken = jwtProvider.generateAccessToken(user);
            return new LoginResponse(newAccessToken, null);
        }
        return new LoginResponse(null, null);
    }

    public LoginResponse getNewRefreshToken(@NonNull String oldRefreshToken) {
        var user = refreshTokenService.findByToken(oldRefreshToken);
        if (user.isEmpty())
            return null;
        return new LoginResponse(null, jwtProvider.generateRefreshToken(user.get().getId()));
    }

    public JwtAuthentication getAuthInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthentication) {
            return (JwtAuthentication) auth;
        } else {
            return null;
        }
    }

    public String hash(String hashableLine) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        return encoder.encode(hashableLine);
    }

    public boolean matchHashes(String line, String hash) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        return encoder.matches(line, hash);
    }

}
