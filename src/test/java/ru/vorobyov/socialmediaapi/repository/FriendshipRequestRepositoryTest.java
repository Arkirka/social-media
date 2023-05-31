package ru.vorobyov.socialmediaapi.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.vorobyov.socialmediaapi.constant.FriendshipStatus;
import ru.vorobyov.socialmediaapi.entity.FriendshipRequest;

@Sql("scripts/add_users.sql")
@SpringBootTest
class FriendshipRequestRepositoryTest {

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public FriendshipRequestRepository friendshipRequestRepository;



    @AfterEach
    void tearDown() {
        friendshipRequestRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void existsBySenderAndRecipient_emptyCase_ShouldReturnFalse() {
        var users = userRepository.findAll();

        Assertions.assertFalse(
                friendshipRequestRepository.existsBySenderAndRecipient(users.get(0), users.get(1))
        );
    }

    @Test
    void existsBySenderAndRecipient_single_ShouldReturnTrue() {
        var users = userRepository.findAll();

        friendshipRequestRepository.save(
                new FriendshipRequest(users.get(0), users.get(1), FriendshipStatus.PENDING)
        );

        Assertions.assertTrue(
                friendshipRequestRepository.existsBySenderAndRecipient(users.get(0), users.get(1))
        );

        Assertions.assertFalse(
                friendshipRequestRepository.existsBySenderAndRecipient(users.get(1), users.get(0))
        );
    }

    @Test
    void existsBySenderAndRecipient_manyCases_ShouldReturnTrueIfExist() {
        var users = userRepository.findAll();

        users.stream().skip(1).forEach(x ->
                        friendshipRequestRepository.save(
                                new FriendshipRequest(users.get(0), x, FriendshipStatus.PENDING)
                        )
                );
        users.stream().skip(1).forEach(x ->
                {
                    Assertions.assertTrue(
                            friendshipRequestRepository.existsBySenderAndRecipient(users.get(0), x)
                    );
                    Assertions.assertFalse(
                            friendshipRequestRepository.existsBySenderAndRecipient(users.get(1), x)
                    );
                    Assertions.assertFalse(
                            friendshipRequestRepository.existsBySenderAndRecipient(users.get(2), x)
                    );
                }
        );
    }

    @Test
    void findBySenderAndRecipient_emptyCase_ShouldNotFind() {
        var users = userRepository.findAll();
        Assertions.assertTrue(
                friendshipRequestRepository
                        .findBySenderAndRecipient(users.get(0), users.get(1)).isEmpty()
        );
    }

    @Test
    void findBySenderAndRecipient_many_ShouldFind() {
        var users = userRepository.findAll();
        var expected = users.stream().skip(1)
                .map(x ->
                        friendshipRequestRepository.save(
                                new FriendshipRequest(users.get(0), x, FriendshipStatus.PENDING)
                        ).getId()
                ).toList();

        users.stream().skip(1).forEach(x ->
                Assertions.assertTrue(expected.contains(
                        friendshipRequestRepository
                                .findBySenderAndRecipient(users.get(0), x).get().getId()
                ))
        );
    }
}