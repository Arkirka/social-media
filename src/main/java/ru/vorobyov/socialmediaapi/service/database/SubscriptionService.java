package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.Subscription;
import ru.vorobyov.socialmediaapi.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface Subscription service for interacting with the database.
 */
public interface SubscriptionService {
    /**
     * Add Subscription.
     *
     * @param subscription the subscription
     * @return the optional of added subscription
     */
    Optional<Subscription> add(Subscription subscription);

    /**
     * Find all subscriptions by subscriber.
     *
     * @param subscriber the subscriber
     * @return the subscription list
     */
    List<Subscription> findAllBySubscriber(User subscriber);

    /**
     * Delete subscription by author.
     *
     * @param author the author
     */
    void deleteByAuthor(User author);
}
