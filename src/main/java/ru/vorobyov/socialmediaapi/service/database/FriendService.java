package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.Friend;

import java.util.Optional;

public interface FriendService {
    Optional<Friend> add(Friend friend);
}
