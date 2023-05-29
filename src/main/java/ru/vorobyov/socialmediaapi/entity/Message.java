package ru.vorobyov.socialmediaapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    public Message() {
    }

    public Message(User sender, String text, Friendship friendship) {
        this.sender = sender;
        this.text = text;
        this.friendship = friendship;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friendship_id", nullable = false)
    private Friendship friendship;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Friendship getDialog() {
        return friendship;
    }

    public void setDialog(Friendship friendship) {
        this.friendship = friendship;
    }
}
