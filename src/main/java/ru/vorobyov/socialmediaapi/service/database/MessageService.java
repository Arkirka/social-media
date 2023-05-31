package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.Message;

import java.util.List;
import java.util.Optional;

/**
 * The interface Message service for interacting with the database.
 */
public interface MessageService {
    /**
     * Add message.
     *
     * @param message the message
     * @return the optional of added message
     */
    Optional<Message> add(Message message);

    /**
     * Find all by friend id list.
     *
     * @param friendId the friend id
     * @return the message list
     */
    List<Message> findAllByFriendId(Long friendId);
}
