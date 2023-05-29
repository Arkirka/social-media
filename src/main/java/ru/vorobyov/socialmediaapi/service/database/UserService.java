package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String login);
    Optional<User> findById(Long userId);
    List<User> findAll();
    Optional<User> add(User user);
}
