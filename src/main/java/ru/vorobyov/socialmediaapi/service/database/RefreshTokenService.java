package ru.vorobyov.socialmediaapi.service.database;



import ru.vorobyov.socialmediaapi.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);
    RefreshToken createRefreshToken(Long userId);
    boolean verify(String token);
}
