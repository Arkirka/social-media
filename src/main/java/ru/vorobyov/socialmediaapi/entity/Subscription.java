package ru.vorobyov.socialmediaapi.entity;

import jakarta.persistence.*;

/**
 * The Subscription entity that represents object at database.
 */
@Entity
@Table(name = "subscriptions")
public class Subscription {
    /**
     * Instantiates a new Subscription.
     */
    public Subscription() {
    }

    /**
     * Instantiates a new Subscription.
     *
     * @param author     the author
     * @param subscriber the subscriber
     */
    public Subscription(User author, User subscriber) {
        this.author = author;
        this.subscriber = subscriber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id")
    private User subscriber;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Gets subscriber.
     *
     * @return the subscriber
     */
    public User getSubscriber() {
        return subscriber;
    }

    /**
     * Sets subscriber.
     *
     * @param subscriber the subscriber
     */
    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }
}
