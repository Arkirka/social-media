package ru.vorobyov.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.vorobyov.socialmediaapi.entity.Image;
import ru.vorobyov.socialmediaapi.entity.Post;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> deleteByPost(@NonNull Post post);
}
