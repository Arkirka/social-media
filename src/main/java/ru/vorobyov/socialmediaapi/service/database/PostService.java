package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> add(Post post);
    List<Post> getAll();
    Optional<Post> getById(Long id);
    Optional<Post> updateById(Long id, Post post);
    void deleteById(Long id);
}
