package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vorobyov.socialmediaapi.entity.Post;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.repository.PostRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * The implementation of Post service.
 */
@Service("postService")
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    /**
     * Instantiates a new Post service.
     *
     * @param postRepository the post repository
     */
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Post> add(Post post) {
        post.setCreatedAt(Instant.now());
        return Optional.of(postRepository.save(post));
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> findAllByAuthorsPaging(List<User> authors, Pageable pageable) {
        var ids = authors.stream().map(User::getId).toList();
        return postRepository.findByUsers(ids, pageable);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Optional<Post> updateById(Long id, Post post) {
        var postOptional = findById(id);
        if (postOptional.isEmpty())
            return Optional.empty();

        Post newPost = postOptional.get();
        newPost.setText(post.getText());
        newPost.setTitle(post.getTitle());

        return Optional.of(postRepository.save(newPost));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

}
