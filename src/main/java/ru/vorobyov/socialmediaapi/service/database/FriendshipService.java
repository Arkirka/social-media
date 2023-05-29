package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipService {
    Optional<Friendship> add(Friendship friendship);
    List<Friendship> findAllByUserId(Long userId);
    Optional<Friendship> findById(Long friendId);
    Optional<Friendship> findByIdAndUserId(Long id, Long userId);
    void deleteById(Long friendId);
}
