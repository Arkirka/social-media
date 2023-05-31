package ru.vorobyov.socialmediaapi.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.vorobyov.socialmediaapi.entity.Subscription;
import ru.vorobyov.socialmediaapi.integration.IntegrationEnvironment;

import java.util.List;

@Sql("scripts/add_users.sql")
@SpringBootTest
class SubscriptionRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        subscriptionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findBySubscriberWithAuthor_existingSubscriber_shouldReturnSubscriptionsWithAuthor() {
        var users = userRepository.findAll();

        var author1 = users.get(0);
        var author2 = users.get(1);
        var subscriber = users.get(2);

        Subscription subscription1 = new Subscription(author1, subscriber);
        subscriptionRepository.save(subscription1);

        Subscription subscription2 = new Subscription(author2, subscriber);
        subscriptionRepository.save(subscription2);

        List<Subscription> subscriptions = subscriptionRepository.findBySubscriberWithAuthor(subscriber);

        Assertions.assertEquals(2, subscriptions.size());
        Assertions.assertEquals(author1.getId(), subscriptions.get(0).getAuthor().getId());
        Assertions.assertEquals(author2.getId(), subscriptions.get(1).getAuthor().getId());
    }

    @Test
    void findBySubscriberWithAuthor_nonExistingSubscriber_shouldReturnEmptyList() {
        var users = userRepository.findAll();

        var subscriber = users.get(0);

        List<Subscription> subscriptions = subscriptionRepository.findBySubscriberWithAuthor(subscriber);

        Assertions.assertTrue(subscriptions.isEmpty());
    }

    @Test
    @Transactional
    void deleteByAuthor_existingAuthor_shouldDeleteSubscriptions() {
        var users = userRepository.findAll();

        var author = users.get(0);
        var subscriber1 = users.get(1);
        var subscriber2 = users.get(2);

        var subscription1 = new Subscription(author, subscriber1);
        subscriptionRepository.save(subscription1);

        var subscription2 = new Subscription(author, subscriber2);
        subscriptionRepository.save(subscription2);

        subscriptionRepository.deleteByAuthor(author);

        var subscriptions =
                subscriptionRepository.findBySubscriberWithAuthor(subscriber1);
        Assertions.assertTrue(subscriptions.isEmpty());

        subscriptions =
                subscriptionRepository.findBySubscriberWithAuthor(subscriber2);
        Assertions.assertTrue(subscriptions.isEmpty());
    }

    @Test
    @Transactional
    void deleteByAuthor_nonExistingAuthor_shouldNotDeleteSubscriptions() {
        var users = userRepository.findAll();

        var author = users.get(0);
        var subscriber = users.get(1);

        var subscription1 = new Subscription(author, subscriber);
        subscriptionRepository.save(subscription1);

        subscriptionRepository.deleteByAuthor(subscriber);

        List<Subscription> subscriptions = subscriptionRepository.findBySubscriberWithAuthor(subscriber);
        Assertions.assertFalse(subscriptions.isEmpty());
    }
}