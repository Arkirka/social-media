package ru.vorobyov.socialmediaapi.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.vorobyov.socialmediaapi.entity.Image;
import ru.vorobyov.socialmediaapi.entity.Post;
import ru.vorobyov.socialmediaapi.integration.IntegrationEnvironment;

import java.util.stream.IntStream;

@Sql("scripts/add_users.sql")
@SpringBootTest
class ImageRepositoryTest extends IntegrationEnvironment {
    @Autowired
    public ImageRepository imageRepository;
    @Autowired
    public PostRepository postRepository;
    @Autowired
    public UserRepository userRepository;


    @AfterEach
    void tearDown() {
        imageRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void deleteByPost_emptyCase_ShouldDeleteNothing() {
        var user = userRepository.findAll().get(0);
        var post = postRepository.save(new Post(user, "title", "text"));
        var post1 = postRepository.save(new Post(user, "title1", "text1"));
        imageRepository.save(new Image("url", post));

        Assertions.assertTrue(imageRepository.deleteByPost(post1).isEmpty());
    }

    @Test
    @Transactional
    void deleteByPost_single_ShouldDeleteOne() {
        var user = userRepository.findAll().get(0);
        var post = postRepository.save(new Post(user, "title", "text"));
        var expected = imageRepository.save(new Image("url", post)).getId();
        var actual = imageRepository.deleteByPost(post).get(0).getId();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void deleteByPost_many_ShouldDeleteOnlyWithSamePost() {
        var user = userRepository.findAll().get(0);
        var post = postRepository.save(new Post(user, "title", "text"));
        var post1 = postRepository.save(new Post(user, "title1", "text1"));

        var expected = IntStream.range(0, 4)
                .mapToObj(x -> imageRepository.save(new Image("url" + x, post))
                        .getId()).toList();
        var notExpected = IntStream.range(0, 4)
                .mapToObj(x -> imageRepository.save(new Image("url" + x, post1))
                        .getId()).toList();

        imageRepository.deleteByPost(post)
                .stream()
                .map(Image::getId)
                .forEach(x -> {
                    Assertions.assertTrue(expected.contains(x));
                    Assertions.assertFalse(notExpected.contains(x));
                });
    }
}