package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.FriendshipRequest;
import ru.vorobyov.socialmediaapi.entity.User;

import java.util.Optional;

@Repository
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {
    boolean existsBySenderAndRecipient(@NonNull User sender, @NonNull User recipient);
    Optional<FriendshipRequest> findBySenderAndRecipient(@NonNull User sender,
                                                         @NonNull User recipient);
}
