package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.RefreshToken;

import java.util.Optional;

/**
 * The interface Refresh token repository.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    /**
     * Find RefreshToken by RefreshToken's property - token.
     *
     * @param token RefreshToken's property - token
     * @return the optional of RefreshToken
     */
    Optional<RefreshToken> findByToken(String token);
}
