package ru.vorobyov.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vorobyov.socialmediaapi.dto.post.ModifyPostRequest;
import ru.vorobyov.socialmediaapi.dto.post.ImageDto;
import ru.vorobyov.socialmediaapi.dto.post.PostDto;
import ru.vorobyov.socialmediaapi.entity.Image;
import ru.vorobyov.socialmediaapi.entity.Post;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.ImageService;
import ru.vorobyov.socialmediaapi.service.database.PostService;
import ru.vorobyov.socialmediaapi.service.database.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Post controller.
 */
@RestController
@RequestMapping("api/posts")
@SecurityRequirement(name = "Bearer Authentication")
public class PostController extends BaseController{

    private final PostService postService;
    private final ImageService imageService;

    public PostController(PostService postService, UserService userService,
                          ImageService imageService) {
        super(userService);
        this.postService = postService;
        this.imageService = imageService;
    }

    /**
     * Create post.
     *
     * @param request the request
     * @return the response entity with status code
     */
    @PostMapping()
    public ResponseEntity<?> add(@RequestBody ModifyPostRequest request) {
        boolean isImagesEmpty = request.getImages() == null || request.getImages().isEmpty();

        if (isPostRequestNotValid(request))
            return new ResponseEntity<>(
                    "Заголовок поста или его текст пусты!",
                    HttpStatus.BAD_REQUEST
            );

        try {
            var postOptional = addPost(request.getTitle(), request.getText());
            boolean isPostCreated = postOptional.isPresent();

            if (!isPostCreated)
                return getServiceUnavailableResponse("Не получилось создать пост! Попробуйте позже!");

            if (isImagesEmpty)
                return new ResponseEntity<>(HttpStatus.CREATED);

            boolean isImageCreated = addImage(postOptional.get(), request.getImages());

            if (!isImageCreated)
                return getServiceUnavailableResponse("Не получилось прикрепить изображения! Попробуйте позже!");

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (EntityNotFoundException e){

            return getNotFoundResponse("Ошибка при загрузке данных пользователя");
        }
    }

    private boolean isPostRequestNotValid(ModifyPostRequest request){
        boolean isTitleEmpty = request.getTitle() == null || request.getTitle().isEmpty();
        boolean isTextEmpty = request.getText() == null || request.getText().isEmpty();

        return isTitleEmpty || isTextEmpty;
    }

    private Optional<Post> addPost(String title, String text) throws EntityNotFoundException{
        User currentUser = getCurrentUser();
        return postService.add(new Post(currentUser, title, text));
    }

    private boolean addImage(Post post,
                             List<ImageDto> imageDtos){
        var imageList = imageService.addAll(
                imageDtos.stream()
                        .map(x -> new Image(x.getImageUrl(), post))
                        .collect(Collectors.toList())
        );

        return !imageList.isEmpty();
    }

    /**
     * Gets all posts.
     *
     * @return all posts.
     */
    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<Post> posts = postService.getAll();
        List<PostDto> response = parsePostListToDto(posts);
        return ResponseEntity.ok(response);
    }

    /**
     * Gets post by id.
     *
     * @param postId the post id
     * @return the post
     */
    @GetMapping("{postId}")
    public ResponseEntity<?> getById(@PathVariable Long postId) {
        var postOptional = postService.getById(postId);
        if (postOptional.isEmpty())
            return getNotFoundResponse("Запись не найдена");

        PostDto response = parsePostToDto(postOptional.get());
        return ResponseEntity.ok(response);
    }

    private List<PostDto> parsePostListToDto(List<Post> posts){
        return posts.stream()
                .map(this::parsePostToDto)
                .toList();
    }

    private PostDto parsePostToDto(Post post) {
        var images = parseImageListToDto(post.getImageList());
        var user = post.getUser();
        var name = user.getFirstName() + " " + user.getLastName();
        return new PostDto(
                post.getId(), name, post.getTitle(), post.getText(), images
        );
    }

    private List<ImageDto> parseImageListToDto(List<Image> images) {
        return images.stream()
                .map(x -> new ImageDto(x.getImageUrl()))
                .toList();
    }

    /**
     * Update post by id.
     *
     * @param postID  the post id
     * @param request the request
     * @return the response entity
     */
    @PutMapping("{postID}")
    public ResponseEntity<?> updateById(@PathVariable Long postID, @RequestBody ModifyPostRequest request) {
        boolean isImagesEmpty = request.getImages() == null || request.getImages().isEmpty();

        if (!isUpdateRequestValid(postID, request))
            return getNotFoundResponse("У вас не найдено такой записи!");

        Post post = parseDtoToPost(request);
        var postOptional = postService.updateById(postID, post);
        if (postOptional.isEmpty())
            return getServiceUnavailableResponse("Не получилось обновить пост! ");

        if (isImagesEmpty)
            return ResponseEntity.noContent().build();

        post = postOptional.get();
        var imageList = parseDtoToImageList(request.getImages(), post);
        imageList = imageService.updateAllByPost(post, imageList);

        if (imageList.isEmpty())
            return getServiceUnavailableResponse("Не получилось обновить изображения!");

        return ResponseEntity.noContent().build();
    }

    private boolean isUpdateRequestValid(Long postID, ModifyPostRequest request){
        var postOptional = postService.getById(postID);
        if (postOptional.isEmpty())
            return false;

        var post = postOptional.get();
        User currentUser = getCurrentUser();

        return currentUser.getId().equals(post.getUser().getId());
    }

    private Post parseDtoToPost(ModifyPostRequest postDto) {
        return new Post(postDto.getTitle(), postDto.getText());
    }

    private List<Image> parseDtoToImageList(List<ImageDto> images, Post post) {
        return images.stream().map(x -> new Image(x.getImageUrl(), post)).toList();
    }

    /**
     * Delete post by id.
     *
     * @param postID the post id
     * @return no content
     */
    @DeleteMapping("{postID}")
    public ResponseEntity<?> deleteById(@PathVariable Long postID) {
        var postOptional = postService.getById(postID);
        if (postOptional.isEmpty())
            return getNotFoundResponse("Пост не найден!");

        imageService.deleteByPost(postOptional.get());
        postService.deleteById(postID);
        return ResponseEntity.noContent().build();
    }
}
