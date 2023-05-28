package ru.vorobyov.socialmediaapi.controller;

import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vorobyov.socialmediaapi.dto.authorization.*;
import ru.vorobyov.socialmediaapi.service.jwt.AuthService;

/**
 * The type Auth controller.
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Login response entity.
     *
     * @param authRequest the auth request
     * @return the new access token
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest authRequest) {
        final LoginResponse token;
        try {
            token = authService.login(authRequest);
        } catch (AuthException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(token);
    }

    /**
     * Register response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank() || request.getPassword() == null || request.getPassword().isBlank())
            return new ResponseEntity<>("Empty login or password", HttpStatus.BAD_REQUEST);
        if (!authService.register(request))
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Gets new access token.
     *
     * @param request the request
     * @return the new access token
     */
    @PostMapping("token")
    public ResponseEntity<?> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final LoginResponse token;
        try {
            token = authService.getAccessToken(request.getRefreshToken(), request.getAccessToken());
        } catch (AuthException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(token);
    }

    /**
     * Gets new refresh token.
     *
     * @param request the request
     * @return the new refresh token
     */
    @PostMapping("refresh")
    public ResponseEntity<?> getNewRefreshToken(@RequestBody NewRefreshJwtRequest request) {
        final LoginResponse token = authService.getNewRefreshToken(request.getRefreshToken());

        return token != null
                ? ResponseEntity.ok(token)
                : new ResponseEntity<>("Not refresh valid token", HttpStatus.UNAUTHORIZED);
    }
}