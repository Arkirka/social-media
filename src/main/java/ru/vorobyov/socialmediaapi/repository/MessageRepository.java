package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.Message;

import java.util.List;

/**
 * The interface Message repository.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    /**
     * Find message list by friendship id.
     *
     * @param id the friendship id
     * @return the message list
     */
    @Query("select t from Message t where t.friendship.id = ?1")
    List<Message> findByFriendshipId(Long id);
}
