package ru.vorobyov.socialmediaapi.entity;

import jakarta.persistence.*;

/**
 * The type Image entity that represents object at database.
 */
@Entity
@Table(name = "images")
public class Image {

    /**
     * Instantiates a new Image.
     */
    public Image() {
    }

    /**
     * Instantiates a new Image.
     *
     * @param imageUrl the image url
     * @param post     the post
     */
    public Image(String imageUrl, Post post) {
        this.imageUrl = imageUrl;
        this.post = post;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * Gets post.
     *
     * @return the post
     */
    public Post getPost() {
        return post;
    }

    /**
     * Sets post.
     *
     * @param post the post
     */
    public void setPost(Post post) {
        this.post = post;
    }


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
     * Gets image url.
     *
     * @return the image url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets image url.
     *
     * @param imageUrl the image url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
