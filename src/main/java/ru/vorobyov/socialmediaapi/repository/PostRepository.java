package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.Post;

import java.util.List;

/**
 * The interface Post repository.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Find paginated posts by users ids and paging/sorting by Pageable object.
     *
     * @param userID   the list of users ids
     * @param pageable the pageable
     * @return the paginated posts
     */
    @Query("select p from Post p JOIN FETCH p.user  where p.user.id in :ids" )
    Page<Post> findByUsers(@Param("ids")List<Long> userID, Pageable pageable);
}
