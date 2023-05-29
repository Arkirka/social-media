package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
}
