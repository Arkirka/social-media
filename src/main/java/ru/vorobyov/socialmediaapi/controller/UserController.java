package ru.vorobyov.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vorobyov.socialmediaapi.constant.FriendshipStatus;
import ru.vorobyov.socialmediaapi.dto.user.UserDto;
import ru.vorobyov.socialmediaapi.entity.Friend;
import ru.vorobyov.socialmediaapi.entity.FriendshipRequest;
import ru.vorobyov.socialmediaapi.entity.Subscription;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.FriendService;
import ru.vorobyov.socialmediaapi.service.database.FriendshipRequestService;
import ru.vorobyov.socialmediaapi.service.database.SubscriptionService;
import ru.vorobyov.socialmediaapi.service.database.UserService;

import java.util.List;

/**
 * The User controller.
 */
@RestController
@RequestMapping("api/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController extends BaseController{

    private final UserService userService;
    private final FriendshipRequestService friendshipRequestService;
    private final SubscriptionService subscriptionService;
    private final FriendService friendService;

    UserController(UserService userService,
                   FriendshipRequestService friendshipRequestService,
                   SubscriptionService subscriptionService,
                   FriendService friendService) {
        super(userService);
        this.userService = userService;
        this.friendshipRequestService = friendshipRequestService;
        this.subscriptionService = subscriptionService;
        this.friendService = friendService;
    }

    /**
     * Get all users.
     *
     * @return response entity object with users objects
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        var userList = userService.findAll();
        if (userList.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(parseUserListToDto(userList));
    }

    private List<UserDto> parseUserListToDto(List<User> userList){
        return userList.stream().map(this::parseUserToDto).toList();
    }

    private UserDto parseUserToDto(User user){
        return new UserDto(
                user.getId(), user.getEmail(),
                user.getFirstName(), user.getLastName()
        );
    }

    /**
     * Send friendship request to user.
     *
     * @param userId the user id
     * @return response entity object with status
     */
    @PostMapping("{userId}/requests")
    public ResponseEntity<?> sendInvite(@PathVariable Long userId) {
        User sender = getCurrentUser();

        var recipientOptional = userService.findById(userId);
        if (recipientOptional.isEmpty())
            return getNotFoundResponse("Пользователь с переданным ID не найден!");

        var recipient = recipientOptional.get();
        if (friendshipRequestService.existBySenderAndRecipient(sender, recipient))
            return getConflictResponse("Запрос на добавление в друзья этому пользователю уже существует!");

        boolean isFriendshipRequestCreated =
                addFriendshipRequest(sender, recipient) && addSubscription(sender, recipient);

        return isFriendshipRequestCreated ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                getServiceUnavailableResponse("Не получилось создать заявку в друзья! Пожалуйста, попробуйте позже!");
    }

    private boolean addFriendshipRequest(User sender, User recipient){
        return friendshipRequestService.add(
                new FriendshipRequest(sender, recipient, FriendshipStatus.PENDING)
        ).isPresent();
    }

    private boolean addSubscription(User subscriber, User author){
        return subscriptionService.add(
                new Subscription(author, subscriber)
        ).isPresent();
    }

    /**
     * Accept friendship request from user.
     *
     * @param userId the user id
     * @return response entity object with status
     */
    @PutMapping("{userId}/requests")
    public ResponseEntity<?> acceptInvite(@PathVariable Long userId) {
        User recipient = getCurrentUser();

        var senderOptional = userService.findById(userId);
        if (senderOptional.isEmpty())
            return getNotFoundResponse("Пользователь с переданным ID не найден!");

        var sender = senderOptional.get();
        if (!friendshipRequestService.existBySenderAndRecipient(sender, recipient))
            return getNotFoundResponse("Заявка на добавление в друзья не найдена!");

        boolean isFriendAdded = addFriend(recipient, sender);

        return isFriendAdded ?
                ResponseEntity.noContent().build() :
                getServiceUnavailableResponse("Не получилось принять заявку! Попробуйте позже!");
    }

    private boolean addFriend(User recipient, User sender){
        return friendshipRequestService.updateToAccepted(sender, recipient).isPresent() &&
                friendService.add(new Friend(recipient, sender)).isPresent() &&
                friendService.add(new Friend(sender, recipient)).isPresent() &&
                addSubscription(recipient, sender);
    }

    /**
     * Reject friendship request from user.
     *
     * @param userId the user id
     * @return response entity object with status
     */
    @DeleteMapping("{userId}/requests")
    public ResponseEntity<?> rejectInvite(@PathVariable Long userId) {
        User recipient = getCurrentUser();

        var senderOptional = userService.findById(userId);
        if (senderOptional.isEmpty())
            return getNotFoundResponse("Пользователь с переданным ID не найден!");

        var sender = senderOptional.get();
        if (!friendshipRequestService.existBySenderAndRecipient(sender, recipient))
            return getNotFoundResponse("Заявка на добавление в друзья не найдена!");

        boolean isRequestUpdated =
                friendshipRequestService.updateToDeclined(sender, recipient).isPresent();

        return isRequestUpdated ?
                ResponseEntity.noContent().build() :
                getServiceUnavailableResponse("Не получилось отклонить заявку! Попробуйте позже!");
    }
}
