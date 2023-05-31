package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vorobyov.socialmediaapi.entity.Subscription;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of Subscription service.
 */
@Service("subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService{
    private final SubscriptionRepository repository;

    /**
     * Instantiates a new Subscription service.
     *
     * @param repository the repository
     */
    public SubscriptionServiceImpl(SubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Subscription> add(Subscription subscription) {
        return Optional.of(repository.save(subscription));
    }

    @Override
    public List<Subscription> findAllBySubscriber(User subscriber) {
        return repository.findBySubscriberWithAuthor(subscriber);
    }

    @Override
    @Transactional
    public void deleteByAuthor(User author) {
        repository.deleteByAuthor(author);
    }
}
