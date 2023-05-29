package ru.vorobyov.socialmediaapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "friendship", indexes = {
        @Index(name = "idx_user_friend", columnList = "user_id, friend_id", unique = true)
})
public class Friendship {

    public Friendship() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
