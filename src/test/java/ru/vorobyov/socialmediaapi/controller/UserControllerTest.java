package ru.vorobyov.socialmediaapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vorobyov.socialmediaapi.dto.user.UserDto;
import ru.vorobyov.socialmediaapi.entity.Friendship;
import ru.vorobyov.socialmediaapi.entity.FriendshipRequest;
import ru.vorobyov.socialmediaapi.entity.Subscription;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.FriendshipRequestService;
import ru.vorobyov.socialmediaapi.service.database.FriendshipService;
import ru.vorobyov.socialmediaapi.service.database.SubscriptionService;
import ru.vorobyov.socialmediaapi.service.database.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private FriendshipRequestService friendshipRequestService;

    @Mock
    private SubscriptionService subscriptionService;

    @Mock
    private FriendshipService friendshipService;

    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService,
                friendshipRequestService, subscriptionService, friendshipService);
    }

    @Test
    public void testGetAll_WithUsers_ReturnsUserList() {
        // Arrange
        List<User> userList = List.of(new User(), new User());
        when(userService.findAll()).thenReturn(userList);

        // Act
        ResponseEntity<?> response = userController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<UserDto> userDtoList = (List<UserDto>) response.getBody();
        assertEquals(userList.size(), userDtoList.size());
    }

    @Test
    public void testGetAll_WithoutUsers_ReturnsNoContent() {
        // Arrange
        when(userService.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = userController.getAll();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testSendInvite_WithValidUserId_ReturnsCreated() {
        // Arrange
        User sender = new User();
        User recipient = new User();
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.of(recipient));
        when(userController.getCurrentUser()).thenReturn(sender);
        when(friendshipRequestService.existBySenderAndRecipient(sender, recipient)).thenReturn(false);
        when(friendshipRequestService.add(any())).thenReturn(Optional.of(new FriendshipRequest()));
        when(subscriptionService.add(any())).thenReturn(Optional.of(new Subscription()));

        // Act
        ResponseEntity<?> response = userController.sendInvite(userId);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testSendInvite_WithInvalidUserId_ReturnsNotFound() {
        // Arrange
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = userController.sendInvite(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSendInvite_WithExistingFriendshipRequest_ReturnsConflict() {
        // Arrange
        User sender = new User();
        User recipient = new User();
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.of(recipient));
        when(userController.getCurrentUser()).thenReturn(sender);
        when(friendshipRequestService.existBySenderAndRecipient(sender, recipient)).thenReturn(true);

        // Act
        ResponseEntity<?> response = userController.sendInvite(userId);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testSendInvite_WithFailedFriendshipRequest_ReturnsServiceUnavailable() {
        // Arrange
        User sender = new User();
        User recipient = new User();
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.of(recipient));
        when(userController.getCurrentUser()).thenReturn(sender);
        when(friendshipRequestService.existBySenderAndRecipient(sender, recipient)).thenReturn(false);
        when(friendshipRequestService.add(any())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = userController.sendInvite(userId);

        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    }

    @Test
    public void testAcceptInvite_WithValidUserId_ReturnsNoContent() {
        // Arrange
        User recipient = new User();
        User sender = new User();
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.of(sender));
        when(userController.getCurrentUser()).thenReturn(recipient);
        when(friendshipRequestService.existBySenderAndRecipient(sender, recipient)).thenReturn(true);
        when(friendshipRequestService.updateToAccepted(sender, recipient)).thenReturn(Optional.of(new FriendshipRequest()));
        when(friendshipService.add(any())).thenReturn(Optional.of(new Friendship()));
        when(subscriptionService.add(any())).thenReturn(Optional.of(new Subscription()));

        // Act
        ResponseEntity<?> response = userController.acceptInvite(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testAcceptInvite_WithInvalidUserId_ReturnsNotFound() {
        // Arrange
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = userController.acceptInvite(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAcceptInvite_WithNonExistentFriendshipRequest_ReturnsNotFound() {
        // Arrange
        User recipient = new User();
        User sender = new User();
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.of(sender));
        when(userController.getCurrentUser()).thenReturn(recipient);
        when(friendshipRequestService.existBySenderAndRecipient(sender, recipient)).thenReturn(false);

        // Act
        ResponseEntity<?> response = userController.acceptInvite(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAcceptInvite_WithFailedFriendAddition_ReturnsServiceUnavailable() {
        // Arrange
        User recipient = new User();
        User sender = new User();
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.of(sender));
        when(userController.getCurrentUser()).thenReturn(recipient);
        when(friendshipRequestService.existBySenderAndRecipient(sender, recipient)).thenReturn(true);
        when(friendshipRequestService.updateToAccepted(sender, recipient)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = userController.acceptInvite(userId);

        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    }

    @Test
    public void testRejectInvite_WithValidUserId_ReturnsNoContent() {
        // Arrange
        User recipient = new User();
        User sender = new User();
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.of(sender));
        when(userController.getCurrentUser()).thenReturn(recipient);
        when(friendshipRequestService.existBySenderAndRecipient(sender, recipient)).thenReturn(true);
        when(friendshipRequestService.updateToDeclined(sender, recipient)).thenReturn(Optional.of(new FriendshipRequest()));

        // Act
        ResponseEntity<?> response = userController.rejectInvite(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testRejectInvite_WithInvalidUserId_ReturnsNotFound() {
        // Arrange
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = userController.rejectInvite(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRejectInvite_WithNonExistentFriendshipRequest_ReturnsNotFound() {
        // Arrange
        User recipient = new User();
        User sender = new User();
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.of(sender));
        when(userController.getCurrentUser()).thenReturn(recipient);
        when(friendshipRequestService.existBySenderAndRecipient(sender, recipient)).thenReturn(false);

        // Act
        ResponseEntity<?> response = userController.rejectInvite(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRejectInvite_WithFailedRequestUpdate_ReturnsServiceUnavailable() {
        // Arrange
        User recipient = new User();
        User sender = new User();
        Long userId = 123L;
        when(userService.findById(userId)).thenReturn(Optional.of(sender));
        when(userController.getCurrentUser()).thenReturn(recipient);
        when(friendshipRequestService.existBySenderAndRecipient(sender, recipient)).thenReturn(true);
        when(friendshipRequestService.updateToDeclined(sender, recipient)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = userController.rejectInvite(userId);

        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    }
}