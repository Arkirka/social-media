package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.stereotype.Service;
import ru.vorobyov.socialmediaapi.entity.Message;
import ru.vorobyov.socialmediaapi.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of Message service.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    /**
     * Instantiates a new Message service.
     *
     * @param messageRepository the message repository
     */
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Optional<Message> add(Message message) {
        return Optional.of(messageRepository.save(message));
    }

    @Override
    public List<Message> findAllByFriendId(Long friendId) {
        return messageRepository.findByFriendshipId(friendId);
    }
}
