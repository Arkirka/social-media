package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.Subscription;

import java.util.Optional;

public interface SubscriptionService {
    Optional<Subscription> add(Subscription subscription);
}