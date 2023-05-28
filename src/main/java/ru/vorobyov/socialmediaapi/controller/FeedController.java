package ru.vorobyov.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/feed")
@SecurityRequirement(name = "Bearer Authentication")
public class FeedController {

    // link:<ссылка>;rel="prev",<ссылка>;rel="next",<ссылка>;rel="last",<ссылка>;rel="first"

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam int page,
                                    @RequestParam int pageSize,
                                    @RequestParam boolean isAscending) {
        // TODO: поглядеть ленту
        return ResponseEntity.ok().build();
    }
}
