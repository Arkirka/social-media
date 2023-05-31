package ru.vorobyov.socialmediaapi.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.vorobyov.socialmediaapi.entity.Friendship;
import ru.vorobyov.socialmediaapi.entity.Message;
import ru.vorobyov.socialmediaapi.integration.IntegrationEnvironment;

import java.util.stream.IntStream;

@Sql("scripts/add_users.sql")
@SpringBootTest
class MessageRepositoryTest extends IntegrationEnvironment {

    @Autowired
    public MessageRepository messageRepository;
    @Autowired
    public FriendshipRepository friendshipRepository;
    @Autowired
    public UserRepository userRepository;


    @AfterEach
    void tearDown() {
        messageRepository.deleteAll();
        friendshipRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    void findByFriendshipId_emptyCase_ShouldNotFind() {
        var users = userRepository.findAll();
        var friendship = friendshipRepository.save(new Friendship(users.get(0), users.get(1)));
        Assertions.assertTrue(
                messageRepository.findByFriendshipId(friendship.getId()).isEmpty()
        );
    }

    @Test
    void findByFriendshipId_single_ShouldFind() {
        var users = userRepository.findAll();
        var friendship = friendshipRepository.save(new Friendship(users.get(0), users.get(1)));
        var expected = messageRepository.save(new Message(users.get(0), "some", friendship)).getId();
        var actual = messageRepository.findByFriendshipId(friendship.getId()).get(0).getId();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByFriendshipId_many_ShouldFind() {
        var users = userRepository.findAll();
        var friendship = friendshipRepository.save(new Friendship(users.get(0), users.get(1)));
        var friendship1 = friendshipRepository.save(new Friendship(users.get(1), users.get(2)));

        var expected = IntStream.of(0, 4)
                .mapToObj(x ->
                        messageRepository.save(new Message(users.get(0), "some" + x, friendship)).getId()
                ).toList();
        var notExpected = IntStream.of(0, 4)
                .mapToObj(x ->
                        messageRepository.save(new Message(users.get(2), "some" + x, friendship1)).getId()
                ).toList();

        messageRepository
                .findByFriendshipId(friendship.getId()).stream()
                .map(Message::getId)
                .forEach(x -> Assertions.assertTrue(expected.contains(x)));

        messageRepository
                .findByFriendshipId(friendship.getId()).stream()
                .map(Message::getId)
                .forEach(x -> Assertions.assertFalse(notExpected.contains(x)));
    }
}