package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.Friendship;

import java.util.List;
import java.util.Optional;

/**
 * The interface Friendship service for interacting with the database.
 */
public interface FriendshipService {
    /**
     * Add Friendship.
     *
     * @param friendship the friendship
     * @return the optional of added Friendship
     */
    Optional<Friendship> add(Friendship friendship);

    /**
     * Find all by user id.
     *
     * @param userId the user id
     * @return the Friendship list
     */
    List<Friendship> findAllByUserId(Long userId);

    /**
     * Find by id.
     *
     * @param friendshipId the friend id
     * @return the optional of Friendship
     */
    Optional<Friendship> findById(Long friendshipId);

    /**
     * Find Friendship by id and user id.
     *
     * @param id     the Friendship id
     * @param userId the user id
     * @return the optional of Friendship
     */
    Optional<Friendship> findByIdAndUserId(Long id, Long userId);

    /**
     * Delete Friendship by id.
     *
     * @param friendshipId the friend id
     */
    void deleteById(Long friendshipId);
}
