package ru.vorobyov.socialmediaapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friendship_id")
    private Friend friend;

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

    public Friend getDialog() {
        return friend;
    }

    public void setDialog(Friend friend) {
        this.friend = friend;
    }
}
