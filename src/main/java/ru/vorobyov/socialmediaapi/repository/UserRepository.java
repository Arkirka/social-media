package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.User;

import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find optional of user by User's property - email.
     *
     * @param email the email
     * @return the optional
     */
    Optional<User> findByEmail(String email);
}