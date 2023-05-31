package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.FriendshipRequest;
import ru.vorobyov.socialmediaapi.entity.User;

import java.util.Optional;

/**
 * The interface Friendship request repository.
 */
@Repository
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {
    /**
     * Exists by sender and recipient boolean.
     *
     * @param sender    the sender
     * @param recipient the recipient
     * @return the boolean
     */
    boolean existsBySenderAndRecipient(@NonNull User sender, @NonNull User recipient);

    /**
     * Find by sender and recipient optional.
     *
     * @param sender    the sender
     * @param recipient the recipient
     * @return the optional of FriendshipRequest
     */
    Optional<FriendshipRequest> findBySenderAndRecipient(@NonNull User sender,
                                                         @NonNull User recipient);
}
