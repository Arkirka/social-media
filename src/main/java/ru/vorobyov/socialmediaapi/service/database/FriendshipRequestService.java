package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.FriendshipRequest;
import ru.vorobyov.socialmediaapi.entity.User;

import java.util.Optional;

/**
 * The interface Friendship request service for interacting with the database.
 */
public interface FriendshipRequestService {
    /**
     * Exist by sender and recipient boolean.
     *
     * @param sender    the sender
     * @param recipient the recipient
     * @return the boolean
     */
    boolean existBySenderAndRecipient(User sender, User recipient);

    /**
     * Add FriendshipRequest.
     *
     * @param request the request
     * @return the optional of added FriendshipRequest
     */
    Optional<FriendshipRequest> add(FriendshipRequest request);

    /**
     * Update to accepted optional.
     *
     * @param sender    the sender
     * @param recipient the recipient
     * @return the optional
     */
    Optional<FriendshipRequest> updateToAccepted(User sender, User recipient);

    /**
     * Update to declined optional.
     *
     * @param sender    the sender
     * @param recipient the recipient
     * @return the optional
     */
    Optional<FriendshipRequest> updateToDeclined(User sender, User recipient);
}
