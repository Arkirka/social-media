package ru.vorobyov.socialmediaapi.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import ru.vorobyov.socialmediaapi.entity.Post;
import ru.vorobyov.socialmediaapi.integration.IntegrationEnvironment;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Sql("scripts/add_users.sql")
@SpringBootTest
class PostRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByUsers_shouldFindPostsByUserIds() {
        var users = userRepository.findAll();

        Post post1 = new Post(users.get(0), "Post 1", "Text 1");
        Post post2 = new Post(users.get(1), "Post 2", "Text 2");
        Post post3 = new Post(users.get(2), "Post 3", "Text 3");
        postRepository.saveAll(Arrays.asList(post1, post2, post3));

        List<Long> userIds = Arrays.asList(users.get(1).getId(), users.get(2).getId());

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> result = postRepository.findByUsers(userIds, pageable);

        Assertions.assertEquals(2, result.getTotalElements());
        Assertions.assertTrue(result.stream().allMatch(post -> userIds.contains(post.getUser().getId())));
    }

    @Test
    void findByUsers_shouldSortPostsByCreatedAtDesc() {
        var users = userRepository.findAll();

        var user1 = users.get(0);
        var user2 = users.get(1);

        var post1 = new Post(user1, "Post 1", "Text 1");
        var post2 = new Post(user2, "Post 2", "Text 2");
        var post3 = new Post(user1, "Post 3", "Text 3");
        var post4 = new Post(user2, "Post 4", "Text 4");

        var now = Instant.now();
        post1.setCreatedAt(now.minusSeconds(4));
        post2.setCreatedAt(now.minusSeconds(2));
        post3.setCreatedAt(now.minusSeconds(3));
        post4.setCreatedAt(now.minusSeconds(1));

        postRepository.saveAll(Arrays.asList(post1, post2, post3, post4));

        List<Long> userIds = Arrays.asList(user1.getId(), user2.getId());

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> result = postRepository.findByUsers(userIds, pageable);

        List<Post> sortedPosts = result.getContent();
        Assertions.assertEquals(4, sortedPosts.size());
        for (int i = 0; i < sortedPosts.size() - 1; i++) {
            Assertions.assertTrue(sortedPosts.get(i).getCreatedAt().isAfter(sortedPosts.get(i + 1).getCreatedAt()));
        }
    }

    @Test
    void findByUsers_shouldSortPostsByCreatedAtAsc() {
        var users = userRepository.findAll();

        var user1 = users.get(0);
        var user2 = users.get(1);

        Post post1 = new Post(user1, "Post 1", "Text 1");
        Post post2 = new Post(user2, "Post 2", "Text 2");
        Post post3 = new Post(user1, "Post 3", "Text 3");
        Post post4 = new Post(user2, "Post 4", "Text 4");

        Instant now = Instant.now();
        post1.setCreatedAt(now.minusSeconds(4));
        post2.setCreatedAt(now.minusSeconds(2));
        post3.setCreatedAt(now.minusSeconds(3));
        post4.setCreatedAt(now.minusSeconds(1));

        postRepository.saveAll(Arrays.asList(post1, post2, post3, post4));

        List<Long> userIds = Arrays.asList(user1.getId(), user2.getId());

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Post> result = postRepository.findByUsers(userIds, pageable);

        List<Post> sortedPosts = result.getContent();
        Assertions.assertEquals(4, sortedPosts.size());
        for (int i = 0; i < sortedPosts.size() - 1; i++) {
            Assertions.assertTrue(sortedPosts.get(i).getCreatedAt().isBefore(sortedPosts.get(i + 1).getCreatedAt()));
        }
    }

}