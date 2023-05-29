package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.stereotype.Service;
import ru.vorobyov.socialmediaapi.entity.Friend;
import ru.vorobyov.socialmediaapi.repository.FriendRepository;

import java.util.Optional;

@Service("friendService")
public class FriendServiceImpl implements FriendService{
    private final FriendRepository repository;

    public FriendServiceImpl(FriendRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Friend> add(Friend friend) {
        return Optional.of(repository.save(friend));
    }
}
