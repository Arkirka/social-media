package ru.vorobyov.socialmediaapi.service.database;

import ru.vorobyov.socialmediaapi.entity.Image;
import ru.vorobyov.socialmediaapi.entity.Post;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Optional<Image> add(Image image);
    List<Image> addAll(List<Image> images);
    List<Image> updateAllByPost(Post post, List<Image> images);
    void deleteByPost(Post post);
}
