package ru.vorobyov.socialmediaapi.entity;

import jakarta.persistence.*;
import ru.vorobyov.socialmediaapi.constant.FriendshipStatus;

/**
 * The type Friendship request entity that represents object at database.
 */
@Entity
@Table(name = "friendship_requests")
public class FriendshipRequest {

    /**
     * Instantiates a new Friendship request.
     */
    public FriendshipRequest() {
    }

    /**
     * Instantiates a new Friendship request.
     *
     * @param sender    the sender
     * @param recipient the recipient
     * @param status    the status
     */
    public FriendshipRequest(User sender, User recipient, FriendshipStatus status) {
        this.sender = sender;
        this.recipient = recipient;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

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
     * Gets recipient.
     *
     * @return the recipient
     */
    public User getRecipient() {
        return recipient;
    }

    /**
     * Sets recipient.
     *
     * @param recipient the recipient
     */
    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public FriendshipStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}

