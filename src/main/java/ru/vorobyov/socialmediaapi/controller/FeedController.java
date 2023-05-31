package ru.vorobyov.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.vorobyov.socialmediaapi.dto.feed.GetAllFeedResponse;
import ru.vorobyov.socialmediaapi.dto.feed.Links;
import ru.vorobyov.socialmediaapi.dto.feed.Metadata;
import ru.vorobyov.socialmediaapi.dto.post.ImageDto;
import ru.vorobyov.socialmediaapi.dto.post.PostDto;
import ru.vorobyov.socialmediaapi.entity.Image;
import ru.vorobyov.socialmediaapi.entity.Post;
import ru.vorobyov.socialmediaapi.entity.Subscription;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.PostService;
import ru.vorobyov.socialmediaapi.service.database.SubscriptionService;
import ru.vorobyov.socialmediaapi.service.database.UserService;

import java.util.List;

/**
 * The type Feed controller.
 */
@RestController
@RequestMapping("api/feed")
@SecurityRequirement(name = "Bearer Authentication")
public class FeedController extends BaseController{
    private final SubscriptionService subscriptionService;
    private final PostService postService;

    /**
     * Instantiates a new Feed controller.
     *
     * @param userService         the user service
     * @param subscriptionService the subscription service
     * @param postService         the post service
     */
    FeedController(UserService userService,
                   SubscriptionService subscriptionService,
                   PostService postService) {
        super(userService);
        this.subscriptionService = subscriptionService;
        this.postService = postService;
    }

    /**
     * Gets all post by subscriptions.
     *
     * @param page        the page number
     * @param pageSize    the page size
     * @param isAscending if it's true then sort in ascending order
     * @return response entity object of header with links and body with metadata and posts
     */
    @GetMapping(params = {"page", "per_page", "isAsc"})
    public ResponseEntity<?> getAll(@RequestParam("page") int page,
                                    @RequestParam("per_page") int pageSize,
                                    @RequestParam("isAsc") boolean isAscending) {
        User currentUser = getCurrentUser();
        var subscriptions =
                subscriptionService.findAllBySubscriber(currentUser);

        if (subscriptions.isEmpty())
            return ResponseEntity.noContent().build();

        Page<Post> postsPage =
                getPostsFromSubscriptions(subscriptions, page, pageSize, isAscending);

        if (postsPage.isEmpty())
            return ResponseEntity.noContent().build();

        var links = buildLinks(postsPage, page, pageSize, isAscending);
        var responseContent = getAllFeedResponse(postsPage, page, pageSize, links);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LINK, getHeader(links));

        return ResponseEntity.ok().headers(headers).body(responseContent);
    }

    private GetAllFeedResponse getAllFeedResponse(Page<Post> postsPage, int page, int pageSize, Links links){
        var response = new GetAllFeedResponse();

        response.setMetadata(new Metadata(
                page, pageSize, postsPage.getTotalPages(),
                postsPage.getTotalElements(), links
        ));

        response.setPosts(parsePostListToDto(postsPage.getContent()));
        return response;
    }

    private String getHeader(Links links) {
        return String.format(
                "<%s>;rel=self,<%s>;rel=first,<%s>;rel=previous,<%s>;rel=next,<%s>;rel=last",
                links.getSelf(), links.getFirst(),
                links.getPrevious(), links.getNext(), links.getLast());

    }


    private Links buildLinks(Page<Post> postPage, int page, int pageSize, boolean isAscending) {
        var result = new Links();
        var baseUri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();

        result.setSelf(UriComponentsBuilder.fromUri(baseUri)
                .queryParam("page", page)
                .queryParam("per_page", pageSize)
                .queryParam("isAsc", isAscending)
                .toUriString());
        result.setFirst(UriComponentsBuilder.fromUri(baseUri)
                .queryParam("page", 0)
                .queryParam("per_page", pageSize)
                .queryParam("isAsc", isAscending)
                .toUriString()
        );
        result.setPrevious(UriComponentsBuilder.fromUri(baseUri)
                .queryParam("page", page == 0 ? 0 : page - 1)
                .queryParam("per_page", pageSize)
                .queryParam("isAsc", isAscending)
                .toUriString()
        );
        result.setNext(UriComponentsBuilder.fromUri(baseUri)
                .queryParam("page", postPage.getTotalPages() != 1 ? page + 1 : page)
                .queryParam("per_page", pageSize)
                .queryParam("isAsc", isAscending)
                .toUriString()
        );
        result.setLast(UriComponentsBuilder.fromUri(baseUri)
                .queryParam("page", postPage.getTotalPages() - 1)
                .queryParam("per_page", pageSize)
                .queryParam("isAsc", isAscending)
                .toUriString()
        );

        return result;
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

    private Page<Post> getPostsFromSubscriptions(List<Subscription> subscriptions,
                                                 int page, int size, boolean isAscending){
        var authors = getAuthorListFromSubscriptionList(subscriptions);

        Sort sort = isAscending ?
                Sort.by( "createdAt").ascending() :
                Sort.by( "createdAt").descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return postService.findAllByAuthorsPaging(authors, pageable);
    }

    private List<User> getAuthorListFromSubscriptionList(List<Subscription> subscriptions){
        return subscriptions.stream().map(Subscription::getAuthor).toList();
    }
}
