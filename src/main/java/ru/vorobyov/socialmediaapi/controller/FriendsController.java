package ru.vorobyov.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/friends")
@SecurityRequirement(name = "Bearer Authentication")
public class FriendsController {

    @GetMapping()
    public ResponseEntity<?> getAll(@PathVariable String postID) {
        // TODO: посмотреть друзей
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteFriend(@PathVariable String userId) {
        // TODO: удалить друзей
        return ResponseEntity.ok().build();
    }
}
