package ru.vorobyov.socialmediaapi.service.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.RefreshTokenService;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * The provider for jwt operation.
 */
@Component
public class JwtProvider {
    private final SecretKey jwtAccessSecret;
    private final RefreshTokenService refreshTokenService;
    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);
    private final Long tokenExpireTimeMins;

    /**
     * Instantiates a new Jwt provider.
     *
     * @param jwtAccessSecret     the jwt access secret
     * @param tokenExpireTimeMins the token expire time mins
     * @param refreshTokenService the refresh token service
     */
    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.access.expirationMins}") Long tokenExpireTimeMins,
            RefreshTokenService refreshTokenService) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.tokenExpireTimeMins = tokenExpireTimeMins;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Generate access token string.
     *
     * @param user the user for whom the access token will be generated
     * @return the access token string
     */
    public String generateAccessToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(tokenExpireTimeMins).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("email", user.getEmail())
                .compact();
    }

    /**
     * Generate refresh token string.
     *
     * @param userId the user for whom the refresh token will be generated
     * @return refresh token string
     * @throws EntityNotFoundException throws if user not found
     */
    public String generateRefreshToken(@NonNull Long userId) throws EntityNotFoundException {
        return refreshTokenService.addByUserId(userId).getToken();
    }

    /**
     * Validate access token.
     *
     * @param accessToken the access token
     * @return return true if access token is valid and false otherwise
     */
    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    /**
     * Validate refresh token.
     *
     * @param refreshToken the refresh token
     * @return return true if refresh token is valid and false otherwise
     */
    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return refreshTokenService.verify(refreshToken);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    /**
     * Gets claims by access token.
     *
     * @param token access token
     * @return access token claims
     */
    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
