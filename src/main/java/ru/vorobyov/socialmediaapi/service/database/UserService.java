package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface User service for interacting with the database.
 */
public interface UserService {
    /**
     * Find User by email.
     *
     * @param email the User's property - email
     * @return the optional of User
     */
    Optional<User> findByEmail(String email);

    /**
     * Find User by id.
     *
     * @param userId the user id
     * @return the optional of User
     */
    Optional<User> findById(Long userId);

    /**
     * Gets a currently logged-in user.
     *
     * @return currently logged-in user
     */
    User getCurrentUser();

    /**
     * Find all user list.
     *
     * @return the user list
     */
    List<User> findAll();

    /**
     * Add user.
     *
     * @param user the user
     * @return the optional of added user
     */
    Optional<User> add(User user);
}
