package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.Friendship;

import java.util.List;
import java.util.Optional;

/**
 * The interface Friend repository.
 */
@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    /**
     * Find by user id or friend id list.
     *
     * @param id the id of user or friend
     * @return the list of Friendship
     */
    @Query("select t from Friendship t where t.user.id = ?1 OR t.friend.id = ?1")
    List<Friendship> findByUserIdOrFriendId(@NonNull Long id);

    /**
     * Find by id and user id optional.
     *
     * @param id     the id friendship
     * @param userId the id of user or friend
     * @return the optional of Friendship
     */
    @Query("select t from Friendship t where t.id = ?1 AND (t.user.id = ?2 OR t.friend.id = ?2)")
    Optional<Friendship> findByIdAndUserId(@NonNull Long id, @NonNull Long userId);
}
