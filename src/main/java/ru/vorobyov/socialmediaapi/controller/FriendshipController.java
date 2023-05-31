package ru.vorobyov.socialmediaapi.controller;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vorobyov.socialmediaapi.dto.friend.AddMessageRequest;
import ru.vorobyov.socialmediaapi.dto.friend.FriendDto;
import ru.vorobyov.socialmediaapi.dto.friend.MessageDto;
import ru.vorobyov.socialmediaapi.entity.Friendship;
import ru.vorobyov.socialmediaapi.entity.Message;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.*;

import java.util.List;

/**
 * The type Friendship controller.
 */
@RestController
@RequestMapping("api/friendship")
@SecurityRequirement(name = "Bearer Authentication")
public class FriendshipController extends BaseController{

    private final FriendshipService friendshipService;
    private final SubscriptionService subscriptionService;
    private final MessageService messageService;

    /**
     * Instantiates a new Friendship controller.
     *
     * @param userService         the user service
     * @param friendshipService   the friendship service
     * @param subscriptionService the subscription service
     * @param messageService      the message service
     */
    FriendshipController(UserService userService,
                         FriendshipService friendshipService,
                         SubscriptionService subscriptionService,
                         MessageService messageService) {
        super(userService);
        this.friendshipService = friendshipService;
        this.subscriptionService = subscriptionService;
        this.messageService = messageService;
    }

    /**
     * Gets all current user friends.
     *
     * @return response entity object with user objects
     */
    @GetMapping()
    public ResponseEntity<?> getAll() {
        User currentUser = getCurrentUser();
        var friendList = friendshipService.findAllByUserId(currentUser.getId());

        boolean hasNoFriend = friendList.isEmpty();
        if (hasNoFriend)
            return ResponseEntity.noContent().build();

        var userDtoList =
                parseFriendListToDtoList(friendList, currentUser.getId());

        return ResponseEntity.ok(userDtoList);
    }

    private List<FriendDto> parseFriendListToDtoList(List<Friendship> friendshipList, Long userId){
        return friendshipList.stream().map(x -> {
            User friend;
            if (x.getFriend().getId().equals(userId))
                friend = x.getUser();
            else
                friend = x.getFriend();

            friend.setId(x.getId());
            return parseFriendToDto(friend);
        }).toList();
    }

    private FriendDto parseFriendToDto(User user){
        return new FriendDto(
                user.getId(), user.getEmail(),
                user.getFirstName(), user.getLastName()
        );
    }

    /**
     * Delete friend response entity.
     *
     * @param friendshipId the friend id
     * @return response entity without content
     */
    @DeleteMapping("{friendshipId}")
    public ResponseEntity<?> deleteFriend(@PathVariable Long friendshipId) {
        var friend = friendshipService.findById(friendshipId);
        if (friend.isEmpty())
            return getNotFoundResponse("Друг с таким id не найден!");

        User author = getFriend(friend.get());
        friendshipService.deleteById(friendshipId);
        subscriptionService.deleteByAuthor(author);

        return ResponseEntity.noContent().build();
    }

    /**
     * Get friend user.
     *
     * @param friendship the friendship
     * @return the user
     */
    User getFriend(Friendship friendship){
        var currentUser = getCurrentUser();
        boolean isFriendFirst =
                friendship.getUser().getId().equals(currentUser.getId());

        return isFriendFirst ?
                friendship.getFriend() :
                friendship.getUser();
    }

    /**
     * Send message to friend.
     *
     * @param friendshipId the friendship id
     * @param request      object that contains the message
     * @return response entity object
     */
    @PostMapping("{friendshipId}/messages")
    public ResponseEntity<?> sendMessage(@PathVariable Long friendshipId,
                                         @RequestBody AddMessageRequest request) {
        User currentUser = getCurrentUser();
        var friendship =
                friendshipService.findByIdAndUserId(friendshipId, currentUser.getId());
        if (friendship.isEmpty())
            return getNotFoundResponse("Друг не найден!");

        boolean isMessageCreated = addMessage(friendship.get(), request.getMessage(), currentUser);

        return isMessageCreated ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                getServiceUnavailableResponse("Не получилось отправить сообщение! Попробуйте позже!");
    }

    private boolean addMessage(Friendship friendship, String text, User currentUser){
        var createdMessage =
                messageService.add(new Message(currentUser, text, friendship));
        return createdMessage.isPresent();
    }

    /**
     * Gets all messages with friend.
     *
     * @param friendshipId the friendship id
     * @return response entity object with all messages
     */
    @GetMapping("{friendshipId}/messages")
    public ResponseEntity<?> getAllMessages(@PathVariable Long friendshipId) {
        // TODO: посмотреть переписку
        User currentUser = getCurrentUser();
        var friendship =
                friendshipService.findByIdAndUserId(friendshipId, currentUser.getId());
        if (friendship.isEmpty())
            return getNotFoundResponse("Друг не найден!");

        var messageList =
                messageService.findAllByFriendId(friendshipId);

        return messageList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(parseMessageListToDto(messageList));
    }

    private List<MessageDto> parseMessageListToDto(List<Message> messageList){
        return messageList.stream().map(this::parseMessageToDto).toList();
    }

    private MessageDto parseMessageToDto(Message message){
        return new MessageDto(
                message.getSender().getEmail(), message.getText()
        );
    }
}
