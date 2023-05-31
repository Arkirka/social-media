package ru.vorobyov.socialmediaapi.service.database;



import ru.vorobyov.socialmediaapi.entity.RefreshToken;

import java.util.Optional;

/**
 * The interface Refresh token service for interacting with the database.
 */
public interface RefreshTokenService {
    /**
     * Find RefreshToken by RefreshToken's property - token.
     *
     * @param token RefreshToken's property - token
     * @return the optional of RefreshToken
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Add RefreshToken by user id.
     *
     * @param userId the user id
     * @return the added refresh token
     */
    RefreshToken addByUserId(Long userId);

    /**
     * Verify expiry of RefreshToken.
     *
     * @param token RefreshToken's property - token
     * @return false if RefreshToken's property is expired and true if not
     */
    boolean verify(String token);
}
