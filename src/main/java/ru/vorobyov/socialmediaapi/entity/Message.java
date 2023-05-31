package ru.vorobyov.socialmediaapi.entity;

import jakarta.persistence.*;

/**
 * The type Message entity that represents object at database.
 */
@Entity
@Table(name = "messages")
public class Message {
    /**
     * Instantiates a new Message.
     */
    public Message() {
    }

    /**
     * Instantiates a new Message.
     *
     * @param sender     the sender
     * @param text       the text
     * @param friendship the friendship
     */
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets sender.
     *
     * @return the sender
     */
    public User getSender() {
        return sender;
    }

    /**
     * Sets sender.
     *
     * @param sender the sender
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets dialog.
     *
     * @return the dialog
     */
    public Friendship getDialog() {
        return friendship;
    }

    /**
     * Sets dialog.
     *
     * @param friendship the friendship
     */
    public void setDialog(Friendship friendship) {
        this.friendship = friendship;
    }
}
