package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vorobyov.socialmediaapi.entity.Post;
import ru.vorobyov.socialmediaapi.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface Post service for interacting with the database.
 */
public interface PostService {
    /**
     * Add Post.
     *
     * @param post the post
     * @return the optional of added post
     */
    Optional<Post> add(Post post);

    /**
     * Find all posts.
     *
     * @return the posts list
     */
    List<Post> findAll();

    /**
     * Find all posts by authors with pageable object.
     *
     * @param authors  the authors
     * @param pageable the pageable
     * @return the posts page
     */
    Page<Post> findAllByAuthorsPaging(List<User> authors, Pageable pageable);

    /**
     * Find post by id.
     *
     * @param id the post id
     * @return the optional of post
     */
    Optional<Post> findById(Long id);

    /**
     * Update post by id.
     *
     * @param id   the post id
     * @param post the post with new properties
     * @return the optional of post
     */
    Optional<Post> updateById(Long id, Post post);

    /**
     * Delete post by id.
     *
     * @param id the post id
     */
    void deleteById(Long id);
}
