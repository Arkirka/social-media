package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.Subscription;
import ru.vorobyov.socialmediaapi.entity.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    Optional<Subscription> add(Subscription subscription);
    List<Subscription> findAllBySubscriber(User subscriber);
    void deleteByAuthor(User author);
}