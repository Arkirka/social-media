package ru.vorobyov.socialmediaapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.vorobyov.socialmediaapi.dto.feed.GetAllFeedResponse;
import ru.vorobyov.socialmediaapi.dto.feed.Links;
import ru.vorobyov.socialmediaapi.dto.feed.Metadata;
import ru.vorobyov.socialmediaapi.dto.post.PostDto;
import ru.vorobyov.socialmediaapi.entity.Post;
import ru.vorobyov.socialmediaapi.entity.Subscription;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.PostService;
import ru.vorobyov.socialmediaapi.service.database.SubscriptionService;
import ru.vorobyov.socialmediaapi.service.database.UserService;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

class FeedControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private SubscriptionService subscriptionService;

    @Mock
    private PostService postService;

    private FeedController feedController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        feedController = new FeedController(userService, subscriptionService, postService);

        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    void testGetAll_withSubscriptionsAndPosts() {
        // Mock data
        User currentUser = new User();
        currentUser.setId(1L);

        List<Subscription> subscriptions = new ArrayList<>();
        Subscription subscription = new Subscription();
        subscription.setAuthor(new User());
        subscriptions.add(subscription);

        Page<Post> postsPage = createDummyPostsPage();

        // Mock dependencies
        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(subscriptionService.findAllBySubscriber(currentUser)).thenReturn(subscriptions);
        when(postService.findAllByAuthorsPaging(anyList(), any())).thenReturn(postsPage);

        // Execute the controller method
        FeedController feedController = new FeedController(userService, subscriptionService, postService);
        ResponseEntity<?> responseEntity = feedController.getAll(0, 10, true);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        GetAllFeedResponse response = (GetAllFeedResponse) responseEntity.getBody();
        assertNotNull(response);

        // Verify the metadata
        Metadata metadata = response.getMetadata();
        assertNotNull(metadata);
        assertEquals(0, metadata.getPage());
        assertEquals(10, metadata.getPerPage());
        assertEquals(1, metadata.getPageCount());
        assertEquals(2, metadata.getTotalCount());

        // Verify the links
        Links links = metadata.getLinks();
        assertNotNull(links);
        assertEquals(buildExpectedLinkHeader(), responseEntity.getHeaders().getFirst(HttpHeaders.LINK));

        // Verify the posts
        List<PostDto> posts = response.getPosts();
        assertNotNull(posts);
        assertEquals(2, posts.size());
        assertEquals("Post 1", posts.get(0).getTitle());
        assertEquals("Post 2", posts.get(1).getTitle());
    }


    @Test
    void testGetAll_withNoSubscriptions() {
        // Mock data
        User currentUser = new User();
        currentUser.setId(1L);

        List<Subscription> subscriptions = Collections.emptyList();

        // Mock dependencies
        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(subscriptionService.findAllBySubscriber(currentUser)).thenReturn(subscriptions);

        ResponseEntity<?> responseEntity = feedController.getAll(0, 10, true);

        // Verify the response
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testGetAll_withNoPosts() {
        // Mock data
        User currentUser = new User();
        currentUser.setId(1L);

        List<Subscription> subscriptions = new ArrayList<>();
        Subscription subscription = new Subscription();
        subscription.setAuthor(new User());
        subscriptions.add(subscription);

        Page<Post> postsPage = Page.empty();

        // Mock dependencies
        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(subscriptionService.findAllBySubscriber(currentUser)).thenReturn(subscriptions);
        when(postService.findAllByAuthorsPaging(anyList(), any())).thenReturn(postsPage);

        ResponseEntity<?> responseEntity = feedController.getAll(0, 10, true);

        // Verify the response
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }



    private Page<Post> createDummyPostsPage() {
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");

        List<Post> posts = new ArrayList<>();

        Post post1 = new Post();
        post1.setId(1L);
        post1.setUser(user1);
        post1.setTitle("Post 1");
        post1.setText("Lorem ipsum dolor sit amet.");
        post1.setImageList(new ArrayList<>());
        post1.setCreatedAt(Instant.now());

        Post post2 = new Post();
        post2.setId(2L);
        post2.setUser(user1);
        post2.setTitle("Post 2");
        post2.setText("Consectetur adipiscing elit.");
        post2.setImageList(new ArrayList<>());
        post2.setCreatedAt(Instant.now().plusSeconds(5000L));

        posts.add(post1);
        posts.add(post2);

        return new PageImpl<>(posts);
    }

    private String buildExpectedLinkHeader() {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        String baseUri = uri.toString();

        return String.format(
                "<%s?page=0&per_page=10&isAsc=true>;rel=self," +
                        "<%s?page=0&per_page=10&isAsc=true>;rel=first," +
                        "<%s?page=0&per_page=10&isAsc=true>;rel=previous," +
                        "<%s?page=0&per_page=10&isAsc=true>;rel=next," +
                        "<%s?page=0&per_page=10&isAsc=true>;rel=last",
                baseUri, baseUri, baseUri, baseUri, baseUri
        );
    }
}