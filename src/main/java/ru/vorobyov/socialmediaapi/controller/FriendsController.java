package ru.vorobyov.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/friends")
@SecurityRequirement(name = "Bearer Authentication")
public class FriendsController {

    @GetMapping()
    public ResponseEntity<?> getAll() {
        // TODO: посмотреть друзей
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable String friendId) {
        // TODO: удалить друзей
        return ResponseEntity.ok().build();
    }

    @PostMapping("{friendId}/messages")
    public ResponseEntity<?> sendMessage(@PathVariable String friendId) {
        // TODO: отправить сообщение другу
        return ResponseEntity.ok().build();
    }

    @GetMapping("{friendId}/messages")
    public ResponseEntity<?> getAllMessages(@PathVariable String friendId) {
        // TODO: посмотреть переписку
        return ResponseEntity.ok().build();
    }
}
