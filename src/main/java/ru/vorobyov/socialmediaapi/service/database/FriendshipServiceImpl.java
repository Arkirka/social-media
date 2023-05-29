package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vorobyov.socialmediaapi.entity.Friendship;
import ru.vorobyov.socialmediaapi.repository.FriendRepository;

import java.util.List;
import java.util.Optional;

@Service("friendService")
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendRepository repository;

    public FriendshipServiceImpl(FriendRepository repository) {
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
    public Optional<Friendship> findById(Long friendId) {
        return repository.findById(friendId);
    }


    @Override
    public Optional<Friendship> findByIdAndUserId(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId);
    }

    @Override
    @Transactional
    public void deleteById(Long friendId) {
        repository.deleteById(friendId);
    }
}
