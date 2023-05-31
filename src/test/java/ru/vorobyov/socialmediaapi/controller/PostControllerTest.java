package ru.vorobyov.socialmediaapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vorobyov.socialmediaapi.dto.post.ModifyPostRequest;
import ru.vorobyov.socialmediaapi.dto.post.PostDto;
import ru.vorobyov.socialmediaapi.entity.Post;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.ImageService;
import ru.vorobyov.socialmediaapi.service.database.PostService;
import ru.vorobyov.socialmediaapi.service.database.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PostControllerTest {
    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private ImageService imageService;

    private PostController postController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        postController = new PostController(postService, userService, imageService);
    }

    @Test
    public void testAddPost_WithValidRequest_ReturnsCreatedStatus() {
        var currentUser = new User();
        currentUser.setId(1L);

        ModifyPostRequest request = new ModifyPostRequest();
        request.setTitle("Test Post");
        request.setText("This is a test post");

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(postService.add(any()))
                .thenReturn(Optional.of(new Post()));

        // Act
        ResponseEntity<?> response = postController.add(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testAddPost_WithInvalidRequest_ReturnsBadRequest() {
        // Arrange
        ModifyPostRequest request = new ModifyPostRequest();

        // Act
        ResponseEntity<?> response = postController.add(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetAllPosts_ReturnsListOfPosts() {
        var currentUser = new User();
        currentUser.setId(1L);
        currentUser.setFirstName("John");
        currentUser.setLastName("Smith");

        var post = new Post(currentUser, "test", "test");
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        posts.add(post);

        when(postService.findAll()).thenReturn(posts);

        // Act
        ResponseEntity<?> response = postController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List<?>);
        List<?> body = (List<?>) response.getBody();
        assertEquals(posts.size(), body.size());
    }

    @Test
    public void testGetPostById_WithValidId_ReturnsPost() {
        var currentUser = new User();
        currentUser.setId(1L);
        currentUser.setFirstName("John");
        currentUser.setLastName("Smith");

        var post = new Post(currentUser, "test", "test");
        Long postId = 1L;
        post.setId(postId);

        when(postService.findById(postId)).thenReturn(Optional.of(post));

        // Act
        ResponseEntity<?> response = postController.getById(postId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof PostDto);
        PostDto body = (PostDto) response.getBody();
        assertEquals(postId, body.getId());
    }

    @Test
    public void testGetPostById_WithInvalidId_ReturnsNotFound() {
        // Arrange
        Long postId = 1L;

        when(postService.findById(postId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = postController.getById(postId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void testUpdatePostById_WithInvalidId_ReturnsNotFound() {
        // Arrange
        Long postId = 1L;
        ModifyPostRequest request = new ModifyPostRequest();

        when(postService.findById(postId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = postController.updateById(postId, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeletePostById_WithValidId_ReturnsNoContent() {
        // Arrange
        Long postId = 1L;

        when(postService.findById(postId)).thenReturn(Optional.of(new Post()));

        // Act
        ResponseEntity<?> response = postController.deleteById(postId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeletePostById_WithInvalidId_ReturnsNotFound() {
        // Arrange
        Long postId = 1L;

        when(postService.findById(postId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = postController.deleteById(postId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Дополнительные тесты для других методов контроллера

}
