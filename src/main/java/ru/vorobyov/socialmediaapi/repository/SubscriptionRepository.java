package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.Subscription;
import ru.vorobyov.socialmediaapi.entity.User;

import java.util.List;

/**
 * The interface Subscription repository.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    /**
     * Find Subscription list by subscriber with author objects.
     *
     * @param subscriber the subscriber
     * @return Subscription list with author objects
     */
    @Query("SELECT s FROM Subscription s JOIN FETCH s.author WHERE s.subscriber = :subscriber")
    List<Subscription> findBySubscriberWithAuthor(@Param("subscriber") User subscriber);

    /**
     * Delete by author.
     *
     * @param author the author
     */
    void deleteByAuthor(@NonNull User author);
}
