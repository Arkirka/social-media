package ru.vorobyov.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vorobyov.socialmediaapi.model.JwtAuthentication;
import ru.vorobyov.socialmediaapi.service.jwt.AuthService;

/**
 * The type Test controller.
 */
@RestController
@RequestMapping("api")
public class TestController {
    private final AuthService authService;

    public TestController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Test response entity.
     *
     * @return the response entity
     */
    @GetMapping("test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hi, u did it!!!!!");
    }

    /**
     * Hello user response entity.
     *
     * @return the response entity
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("hello/user")
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("Hello user !");
    }

    /**
     * Hello admin response entity.
     *
     * @return the response entity
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("hello/admin")
    public ResponseEntity<String> helloAdmin() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello admin " + authInfo.getPrincipal() + "!");
    }
}
