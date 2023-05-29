package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Optional<Message> add(Message message);
    List<Message> findAllByFriendId(Long friendId);
}
