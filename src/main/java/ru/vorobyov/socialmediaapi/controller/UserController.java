package ru.vorobyov.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.UserService;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("api/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController extends BaseController{

    private final UserService userService;

    UserController(UserService userService, UserService userService1) {
        super(userService);
        this.userService = userService1;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return null;
    }

    @PostMapping("{userId}/requests")
    public ResponseEntity<?> sendInvite(@PathVariable Long userId) {
        // TODO: заявка в друзья: сделать рекветс на добавление и добавить в подписчики
        // получить юзера, получить второго юзера, проверить что не было реквеста, создать реквест
        User sender;
        try {
            sender = getCurrentUser();
        } catch (EntityNotFoundException e){
            return getNotFoundResponse("Ошибка при загрузке данных пользователя!");
        }
        var recipientOptional = userService.findById(userId);
        if (recipientOptional.isEmpty())
            return getNotFoundResponse("Пользователь с переданным ID не найден!");
        return ResponseEntity.ok().build();
    }

    @PutMapping("{userId}/requests")
    public ResponseEntity<?> acceptInvite(@PathVariable String userId) {
        // TODO: принять заявку: сменить статут реквеста, добавить в друзья 2 записи с двумя пользователями, сделать пользователя подписчиком
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{userId}/requests")
    public ResponseEntity<?> rejectInvite(@PathVariable String userId) {
        // TODO: отклонить заявку: сменить статус заявки
        return ResponseEntity.ok().build();
    }
}
