package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vorobyov.socialmediaapi.entity.Post;
import ru.vorobyov.socialmediaapi.entity.User;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> add(Post post);
    List<Post> findAll();
    Page<Post> findAllByAuthorsPaging(List<User> authors, Pageable pageable);
    Optional<Post> findById(Long id);
    Optional<Post> updateById(Long id, Post post);
    void deleteById(Long id);
}
