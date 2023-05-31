package ru.vorobyov.socialmediaapi.service.jwt;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.vorobyov.socialmediaapi.dto.JwtRequest;
import ru.vorobyov.socialmediaapi.dto.JwtResponse;
import ru.vorobyov.socialmediaapi.dto.RegisterRequest;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.model.JwtAuthentication;
import ru.vorobyov.socialmediaapi.service.database.RefreshTokenService;
import ru.vorobyov.socialmediaapi.service.database.UserService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final User user = userService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user.getId());
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public boolean register(@NonNull RegisterRequest request){
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return userService.create(user).isPresent();
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken, @NonNull String accessToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getAccessClaims(accessToken);
            final String login = claims.getSubject();
            final User user = userService.getByLogin(login)
                    .orElseThrow(() -> new AuthException("Пользователь не найден"));
            var refreshTokenOptional = refreshTokenService.findByToken(refreshToken);
            if (refreshTokenOptional.isEmpty() || !refreshTokenOptional.get().getToken().equals(refreshToken))
                throw new AuthException("Токен не найден");
            final String newAccessToken = jwtProvider.generateAccessToken(user);
            return new JwtResponse(newAccessToken, null);
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse getNewRefreshToken(@NonNull String oldRefreshToken) {
        var user = refreshTokenService.findByToken(oldRefreshToken);
        if (user.isEmpty())
            return null;
        return new JwtResponse(null, jwtProvider.generateRefreshToken(user.get().getId()));
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
