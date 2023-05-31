package ru.vorobyov.socialmediaapi.controller;

import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vorobyov.socialmediaapi.dto.authorization.*;
import ru.vorobyov.socialmediaapi.service.database.UserService;
import ru.vorobyov.socialmediaapi.service.jwt.AuthService;

/**
 * Controller with authentication and authorization methods
 */
@RestController
@RequestMapping("api/auth")
public class AuthController extends BaseController{
    private final AuthService authService;

    /**
     * Instantiates a new Auth controller.
     *
     * @param authService the auth service
     * @param userService the user service
     */
    public AuthController(AuthService authService, UserService userService) {
        super(userService);
        this.authService = authService;
    }

    /**
     * Method for logging in and getting a temporary access token
     *
     * @param authRequest An object with login credentials
     * @return New temporary access token
     * @throws AuthException the auth exception
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest authRequest) throws AuthException {
        final LoginResponse token = authService.login(authRequest);
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
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method for obtaining new temporary access token
     *
     * @param request An object with data for updating the access token
     * @return new temporary access token
     * @throws AuthException the auth exception
     */
    @PostMapping("token")
    public ResponseEntity<?> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final NewAccessTokenResponse token =
                authService.getAccessToken(request.getRefreshToken(), request.getAccessToken());
        return ResponseEntity.ok(token);
    }

    /**
     * Method for obtaining a new refresh token.
     *
     * @param request An object with data for updating the refresh token
     * @return new refresh token
     */
    @PostMapping("refresh")
    public ResponseEntity<?> getNewRefreshToken(@RequestBody NewRefreshJwtRequest request) {
        final var token = authService.getNewRefreshToken(request.getRefreshToken());

        return token != null
                ? ResponseEntity.ok(token)
                : new ResponseEntity<>(
                        "Не действительный токен повторного обновления",
                HttpStatus.UNAUTHORIZED
        );
    }
}