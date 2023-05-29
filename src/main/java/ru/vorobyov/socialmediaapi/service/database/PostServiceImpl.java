package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vorobyov.socialmediaapi.entity.Post;
import ru.vorobyov.socialmediaapi.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service("postService")
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Post> add(Post post) {
        return Optional.of(postRepository.save(post));
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
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
