package ru.vorobyov.socialmediaapi.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.integration.IntegrationEnvironment;

import java.util.Optional;

@Sql("scripts/add_users.sql")
@SpringBootTest
class UserRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmail_existingEmail_shouldReturnUser() {
        var user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);

        var optionalUser = userRepository.findByEmail("test@example.com");
        Assertions.assertTrue(optionalUser.isPresent());

        var foundUser = optionalUser.get();
        Assertions.assertEquals("test@example.com", foundUser.getEmail());
        Assertions.assertEquals("John", foundUser.getFirstName());
        Assertions.assertEquals("Doe", foundUser.getLastName());
    }

    @Test
    void findByEmail_nonExistingEmail_shouldReturnEmptyOptional() {
        Optional<User> optionalUser = userRepository.findByEmail("nonexisting@example.com");

        Assertions.assertFalse(optionalUser.isPresent());
    }
}