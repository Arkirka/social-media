package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.Subscription;
import ru.vorobyov.socialmediaapi.entity.User;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    long deleteByAuthor(@NonNull User author);
}
