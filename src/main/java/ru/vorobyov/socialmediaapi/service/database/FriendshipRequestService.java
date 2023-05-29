package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.FriendshipRequest;
import ru.vorobyov.socialmediaapi.entity.User;

import java.util.Optional;

public interface FriendshipRequestService {
    boolean existBySenderAndRecipient(User sender, User recipient);
    Optional<FriendshipRequest> add(FriendshipRequest request);
    Optional<FriendshipRequest> updateToAccepted(User sender, User recipient);
    Optional<FriendshipRequest> updateToDeclined(User sender, User recipient);
}
