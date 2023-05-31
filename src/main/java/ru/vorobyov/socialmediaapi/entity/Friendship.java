package ru.vorobyov.socialmediaapi.entity;

import jakarta.persistence.*;

/**
 * The Friendship entity that represents object at database.
 */
@Entity
@Table(name = "friendship", indexes = {
        @Index(name = "idx_user_friend", columnList = "user_id, friend_id", unique = true)
})
public class Friendship {

    /**
     * Instantiates a new Friendship.
     */
    public Friendship() {
    }

    /**
     * Instantiates a new Friendship.
     *
     * @param user   the user
     * @param friend the friend
     */
    public Friendship(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User friend;

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
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets friend.
     *
     * @return the friend
     */
    public User getFriend() {
        return friend;
    }

    /**
     * Sets friend.
     *
     * @param friend the friend
     */
    public void setFriend(User friend) {
        this.friend = friend;
    }
}
