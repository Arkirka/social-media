package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.Image;
import ru.vorobyov.socialmediaapi.entity.Post;

import java.util.List;

/**
 * The interface Image repository.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    /**
     * Delete list of images by post object.
     *
     * @param post the post
     * @return the list
     */
    List<Image> deleteByPost(@NonNull Post post);
}
