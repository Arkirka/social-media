package ru.vorobyov.socialmediaapi.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.vorobyov.socialmediaapi.entity.RefreshToken;
import ru.vorobyov.socialmediaapi.integration.IntegrationEnvironment;

import java.time.Instant;
import java.util.Optional;

@Sql("scripts/add_users.sql")
@SpringBootTest
class RefreshTokenRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        refreshTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByToken_existingToken_shouldReturnRefreshToken() {
        var users = userRepository.findAll();
        userRepository.save(users.get(0));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(users.get(0));
        refreshToken.setToken("testToken");
        refreshToken.setExpiryDate(Instant.now().plusSeconds(3600));
        refreshTokenRepository.save(refreshToken);

        var foundToken = refreshTokenRepository.findByToken("testToken");

        Assertions.assertTrue(foundToken.isPresent());
        Assertions.assertEquals("testToken", foundToken.get().getToken());
    }

    @Test
    void findByToken_nonExistingToken_shouldReturnEmptyOptional() {
        Optional<RefreshToken> foundToken = refreshTokenRepository.findByToken("nonExistingToken");

        Assertions.assertTrue(foundToken.isEmpty());
    }
}