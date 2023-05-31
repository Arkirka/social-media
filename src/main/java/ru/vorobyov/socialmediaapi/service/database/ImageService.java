package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.Image;
import ru.vorobyov.socialmediaapi.entity.Post;

import java.util.List;
import java.util.Optional;

/**
 * The interface Image service for interacting with the database.
 */
public interface ImageService {
    /**
     * Add image.
     *
     * @param image the image
     * @return the optional of image
     */
    Optional<Image> add(Image image);

    /**
     * Add all list.
     *
     * @param images the images
     * @return all added images
     */
    List<Image> addAll(List<Image> images);

    /**
     * Update all by post list.
     *
     * @param post   the post
     * @param images the images
     * @return all updated images
     */
    List<Image> updateAllByPost(Post post, List<Image> images);

    /**
     * Delete by post.
     *
     * @param post the post
     */
    void deleteByPost(Post post);
}
