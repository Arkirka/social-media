package ru.vorobyov.socialmediaapi.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

/**
 * The type Post entity that represents object at database.
 */
@Entity
@Table(name = "posts")
public class Post{

    /**
     * Instantiates a new Post.
     */
    public Post() {
    }

    /**
     * Instantiates a new Post.
     *
     * @param title the title
     * @param text  the text
     */
    public Post(String title, String text) {
        this.title = title;
        this.text = text;
    }

    /**
     * Instantiates a new Post.
     *
     * @param user  the user
     * @param title the title
     * @param text  the text
     */
    public Post(User user, String title, String text) {
        this.user = user;
        this.title = title;
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    private String text;

    @Column(name = "createdAt")
    private Instant createdAt;

    @OneToMany(mappedBy = "post")
    private List<Image> imageList;

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
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Gets created at.
     *
     * @return the created at
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets image list.
     *
     * @return the image list
     */
    public List<Image> getImageList() {
        return imageList;
    }

    /**
     * Sets image list.
     *
     * @param imageList the image list
     */
    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}

