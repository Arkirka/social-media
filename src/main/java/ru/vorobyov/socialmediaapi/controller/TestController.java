package ru.vorobyov.socialmediaapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vorobyov.socialmediaapi.model.JwtAuthentication;
import ru.vorobyov.socialmediaapi.service.jwt.AuthService;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class TestController {
    private final AuthService authService;

    @GetMapping("test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hi, u did it!!!!!");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("hello/user")
    public ResponseEntity<String> helloUser() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("hello/admin")
    public ResponseEntity<String> helloAdmin() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello admin " + authInfo.getPrincipal() + "!");
    }
}
