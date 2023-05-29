package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.Friendship;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friendship, Long> {
    @Query("select t from Friendship t where t.user.id = ?1 OR t.friend.id = ?1")
    List<Friendship> findByUserIdOrFriendId(@NonNull Long id);

    @Query("select t from Friendship t where t.id = ?1 AND (t.user.id = ?2 OR t.friend.id = ?2)")
    Optional<Friendship> findByIdAndUserId(@NonNull Long id, @NonNull Long userId);
}
