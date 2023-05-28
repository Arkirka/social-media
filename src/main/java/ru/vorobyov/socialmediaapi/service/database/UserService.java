package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByEmail(String login);
    Optional<User> findById(Long userId);
    Optional<User> create(User user);
}
