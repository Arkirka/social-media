package ru.vorobyov.socialmediaapi.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.vorobyov.socialmediaapi.entity.Friendship;
import ru.vorobyov.socialmediaapi.integration.IntegrationEnvironment;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Sql("scripts/add_users.sql")
@SpringBootTest
class FriendshipRepositoryTest extends IntegrationEnvironment {

    @Autowired
    public FriendshipRepository friendshipRepository;
    @Autowired
    public UserRepository userRepository;


    @AfterEach
    void tearDown() {
        friendshipRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByUserIdOrFriendId_emptyCases_shouldNotFind() {
        friendshipRepository.deleteAll();

        Assertions.assertTrue(friendshipRepository.findByUserIdOrFriendId(1L).isEmpty());
    }

    @Test
    void findByUserIdOrFriendId_single_byUserId_shouldFind() {
        var users = userRepository.findAll();
        var user = users.get(0);
        var expected = friendshipRepository.save(new Friendship(user, users.get(1))).getId();

        var actual = friendshipRepository.findByUserIdOrFriendId(user.getId()).get(0).getId();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByUserIdOrFriendId_single_byFriendId_shouldFind() {
        var users = userRepository.findAll();
        var friend = users.get(1);
        var expected = friendshipRepository.save(new Friendship(users.get(0), friend)).getId();

        var actual = friendshipRepository.findByUserIdOrFriendId(friend.getId()).get(0).getId();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByUserIdOrFriendId_byUserId_shouldFindAll() {
        var users = userRepository.findAll();
        var user = users.get(0);
        var expected =
                IntStream.range(1, users.size())
                        .mapToObj(x ->
                                friendshipRepository.save(new Friendship(user, users.get(x))).getId()
                        ).toList();

        friendshipRepository.findByUserIdOrFriendId(user.getId())
                .forEach(x -> Assertions.assertTrue(expected.contains(x.getId())));
    }

    @Test
    void findByUserIdOrFriendId_byFriendId_shouldFindAll() {
        var users = userRepository.findAll();
        var friend = users.get(1);
        var expected =
                IntStream.range(1, users.size())
                        .mapToObj(x ->
                                friendshipRepository.save(new Friendship(users.get(x), friend)).getId()
                        ).toList();

        friendshipRepository.findByUserIdOrFriendId(friend.getId())
                .forEach(x -> Assertions.assertTrue(expected.contains(x.getId())));
    }

    @Test
    void findByIdAndUserId_emptyCase_shouldNotFind() {
        friendshipRepository.deleteAll();
        var users = userRepository.findAll();

        LongStream.range(0L, users.size())
                .mapToObj(x ->
                        friendshipRepository.findByIdAndUserId(0L, users.get(0).getId())
                ).forEach(x -> Assertions.assertTrue(x.isEmpty()));
    }

    @Test
    void findByIdAndFriendId_single_byUserId_shouldFind() {
        var users = userRepository.findAll();
        var user = users.get(0);
        var expected = friendshipRepository.save(new Friendship(user, users.get(1))).getId();

        var actual = friendshipRepository.findByIdAndUserId(expected, user.getId()).get().getId();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdAndFriendId_single_byFriendId_shouldFind() {
        var users = userRepository.findAll();
        var friend = users.get(1);
        var expected = friendshipRepository.save(new Friendship(users.get(0), friend)).getId();

        var actual = friendshipRepository.findByIdAndUserId(expected, friend.getId()).get().getId();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdAndFriendId_byUserId_shouldFindAll() {
        var users = userRepository.findAll();
        var user = users.get(0);

        IntStream.range(1, users.size())
                .forEach(x ->
                        {
                            var expected =
                                    friendshipRepository.save(new Friendship(user, users.get(x))).getId();
                            var actual =
                                    friendshipRepository.findByIdAndUserId(expected, user.getId()).get().getId();
                            Assertions.assertEquals(expected, actual);
                        }

                );
    }

    @Test
    void findByIdAndFriendId_byFriendId_shouldFindAll() {
        var users = userRepository.findAll();
        var friend = users.get(1);

        IntStream.range(1, users.size())
                .forEach(x ->
                        {
                            var expected =
                                    friendshipRepository.save(new Friendship(users.get(x), friend)).getId();
                            var actual =
                                    friendshipRepository.findByIdAndUserId(expected, friend.getId()).get().getId();
                            Assertions.assertEquals(expected, actual);
                        }

                );
    }
}