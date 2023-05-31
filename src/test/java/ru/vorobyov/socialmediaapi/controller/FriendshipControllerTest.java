package ru.vorobyov.socialmediaapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vorobyov.socialmediaapi.dto.friend.AddMessageRequest;
import ru.vorobyov.socialmediaapi.dto.friend.FriendDto;
import ru.vorobyov.socialmediaapi.dto.friend.MessageDto;
import ru.vorobyov.socialmediaapi.entity.Friendship;
import ru.vorobyov.socialmediaapi.entity.Message;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.FriendshipService;
import ru.vorobyov.socialmediaapi.service.database.MessageService;
import ru.vorobyov.socialmediaapi.service.database.SubscriptionService;
import ru.vorobyov.socialmediaapi.service.database.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FriendshipControllerTest {

    private FriendshipController friendshipController;

    @Mock
    private FriendshipService friendshipService;

    @Mock
    private UserService userService;

    @Mock
    private SubscriptionService subscriptionService;

    @Mock
    private MessageService messageService;

    public FriendshipControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        friendshipController =
                new FriendshipController(userService,
                        friendshipService, subscriptionService, messageService);
    }

    @Test
    void getAll_ShouldReturnFriendList_WhenFriendsExist() {
        User currentUser = new User();
        currentUser.setId(1L);

        User friend = new User();
        friend.setId(2L);

        Friendship friendship1 = new Friendship();
        friendship1.setId(2L);
        friendship1.setUser(currentUser);
        friendship1.setFriend(friend);

        Friendship friendship2 = new Friendship();
        friendship2.setId(3L);
        friendship2.setUser(currentUser);
        friendship2.setFriend(friend);

        List<Friendship> friendshipList = new ArrayList<>();
        friendshipList.add(friendship1);
        friendshipList.add(friendship2);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(friendshipService.findAllByUserId(currentUser.getId())).thenReturn(friendshipList);

        ResponseEntity<?> responseEntity = friendshipController.getAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<FriendDto> friendDtoList = (List<FriendDto>) responseEntity.getBody();
        assert friendDtoList != null;
        assertEquals(2, friendDtoList.size());
    }

    @Test
    void getAll_ShouldReturnNoContent_WhenNoFriendsExist() {
        User currentUser = new User();
        currentUser.setId(1L);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(friendshipService.findAllByUserId(currentUser.getId())).thenReturn(new ArrayList<>());

        ResponseEntity<?> responseEntity = friendshipController.getAll();

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void deleteFriend_ShouldReturnNoContent_WhenFriendExists() {
        User currentUser = new User();
        currentUser.setId(1L);

        User friend = new User();
        currentUser.setId(2L);

        Long friendshipId = 1L;
        Friendship friendship = new Friendship();
        friendship.setId(friendshipId);
        friendship.setUser(currentUser);
        friendship.setFriend(friend);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(friendshipService.findById(friendshipId)).thenReturn(Optional.of(friendship));

        ResponseEntity<?> responseEntity = friendshipController.deleteFriend(friendshipId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(friendshipService, times(1)).deleteById(friendshipId);
        verify(subscriptionService, times(1)).deleteByAuthor(any(User.class));
    }

    @Test
    void deleteFriend_ShouldReturnNotFound_WhenFriendDoesNotExist() {
        Long friendshipId = 1L;
        when(friendshipService.findById(friendshipId)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = friendshipController.deleteFriend(friendshipId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(friendshipService, never()).deleteById(anyLong());
        verify(subscriptionService, never()).deleteByAuthor(any(User.class));
    }

    @Test
    void sendMessage_ShouldReturnCreated_WhenFriendExists() {
        Long friendshipId = 1L;
        String messageText = "Hello!";
        AddMessageRequest addMessageRequest = new AddMessageRequest();
        addMessageRequest.setMessage(messageText);

        User currentUser = new User();
        currentUser.setId(2L);

        Friendship friendship = new Friendship();
        friendship.setId(friendshipId);
        friendship.setUser(currentUser);
        friendship.setFriend(new User());

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(friendshipService.findByIdAndUserId(friendshipId, currentUser.getId()))
                .thenReturn(Optional.of(friendship));
        when(messageService.add(any(Message.class))).thenReturn(Optional.of(new Message()));

        ResponseEntity<?> responseEntity = friendshipController.sendMessage(friendshipId, addMessageRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(messageService, times(1)).add(any(Message.class));
    }

    @Test
    void sendMessage_ShouldReturnNotFound_WhenFriendDoesNotExist() {
        Long friendshipId = 1L;
        AddMessageRequest addMessageRequest = new AddMessageRequest();
        addMessageRequest.setMessage("Hello!");

        User currentUser = new User();
        currentUser.setId(2L);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(friendshipService.findByIdAndUserId(friendshipId, currentUser.getId()))
                .thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = friendshipController.sendMessage(friendshipId, addMessageRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(messageService, never()).add(any(Message.class));
    }

    @Test
    void getAllMessages_ShouldReturnMessageList_WhenFriendExists() {
        Long friendshipId = 1L;

        User currentUser = new User();
        currentUser.setId(2L);

        Friendship friendship = new Friendship();
        friendship.setId(friendshipId);
        friendship.setUser(currentUser);
        friendship.setFriend(new User());

        Message message1 = new Message();
        message1.setSender(new User());
        message1.setText("Message 1");

        Message message2 = new Message();
        message2.setSender(new User());
        message2.setText("Message 2");

        List<Message> messageList = new ArrayList<>();
        messageList.add(message1);
        messageList.add(message2);

        when(friendshipService.findByIdAndUserId(friendshipId, currentUser.getId()))
                .thenReturn(Optional.of(friendship));
        when(messageService.findAllByFriendId(friendshipId)).thenReturn(messageList);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        ResponseEntity<?> responseEntity = friendshipController.getAllMessages(friendshipId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<MessageDto> messageDtoList = (List<MessageDto>) responseEntity.getBody();
        assertEquals(2, messageDtoList.size());
    }

    @Test
    void getAllMessages_ShouldReturnNoContent_WhenFriendDoesNotExist() {
        Long friendshipId = 1L;

        User currentUser = new User();
        currentUser.setId(2L);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(friendshipService.findByIdAndUserId(friendshipId, currentUser.getId()))
                .thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = friendshipController.getAllMessages(friendshipId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
