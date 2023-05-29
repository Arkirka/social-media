package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.stereotype.Service;
import ru.vorobyov.socialmediaapi.entity.Subscription;
import ru.vorobyov.socialmediaapi.repository.SubscriptionRepository;

import java.util.Optional;

@Service("subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService{
    private final SubscriptionRepository repository;

    public SubscriptionServiceImpl(SubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Subscription> add(Subscription subscription) {
        return Optional.of(repository.save(subscription));
    }
}
