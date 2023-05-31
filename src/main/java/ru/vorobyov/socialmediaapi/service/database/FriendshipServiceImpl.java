package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vorobyov.socialmediaapi.entity.Friendship;
import ru.vorobyov.socialmediaapi.repository.FriendshipRepository;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of Friendship service.
 */
@Service("friendService")
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository repository;

    /**
     * Instantiates a new Friendship service.
     *
     * @param repository the repository
     */
    public FriendshipServiceImpl(FriendshipRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Friendship> add(Friendship friendship) {
        return Optional.of(repository.save(friendship));
    }

    @Override
    public List<Friendship> findAllByUserId(Long userId) {
        return repository.findByUserIdOrFriendId(userId);
    }

    @Override
    public Optional<Friendship> findById(Long friendshipId) {
        return repository.findById(friendshipId);
    }


    @Override
    public Optional<Friendship> findByIdAndUserId(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId);
    }

    @Override
    @Transactional
    public void deleteById(Long friendshipId) {
        repository.deleteById(friendshipId);
    }
}
