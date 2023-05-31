package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.stereotype.Service;
import ru.vorobyov.socialmediaapi.constant.FriendshipStatus;
import ru.vorobyov.socialmediaapi.entity.FriendshipRequest;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.repository.FriendshipRequestRepository;

import java.util.Optional;

/**
 * The implementation of Friendship request service.
 */
@Service("friendshipRequestService")
public class FriendshipRequestServiceImpl implements FriendshipRequestService{
    private final FriendshipRequestRepository repository;

    /**
     * Instantiates a new Friendship request service.
     *
     * @param repository the repository
     */
    public FriendshipRequestServiceImpl(FriendshipRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existBySenderAndRecipient(User sender, User recipient) {
        return repository.existsBySenderAndRecipient(sender, recipient);
    }

    @Override
    public Optional<FriendshipRequest> add(FriendshipRequest request) {
        return Optional.of(repository.save(request));
    }

    @Override
    public Optional<FriendshipRequest> updateToAccepted(User sender, User recipient) {
        var entityOptional = repository.findBySenderAndRecipient(sender, recipient);
        if (entityOptional.isEmpty())
            return Optional.empty();

        var entity = entityOptional.get();
        entity.setStatus(FriendshipStatus.ACCEPTED);
        return Optional.of(repository.save(entity));
    }

    @Override
    public Optional<FriendshipRequest> updateToDeclined(User sender, User recipient) {
        var entityOptional = repository.findBySenderAndRecipient(sender, recipient);
        if (entityOptional.isEmpty())
            return Optional.empty();

        var entity = entityOptional.get();
        entity.setStatus(FriendshipStatus.DECLINED);
        return Optional.of(repository.save(entity));
    }
}
