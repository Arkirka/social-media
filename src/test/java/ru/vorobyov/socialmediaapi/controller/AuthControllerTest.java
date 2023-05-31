package ru.vorobyov.socialmediaapi.controller;

import jakarta.security.auth.message.AuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vorobyov.socialmediaapi.dto.authorization.*;
import ru.vorobyov.socialmediaapi.service.database.UserService;
import ru.vorobyov.socialmediaapi.service.jwt.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthControllerTest {
    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(authService, userService);
    }

    @Test
    void login_validCredentials_shouldReturnAccessToken() throws AuthException {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");
        LoginResponse expectedResponse = new LoginResponse("accessToken", "refreshToken");

        when(authService.login(loginRequest)).thenReturn(expectedResponse);

        ResponseEntity<?> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void register_validRequest_shouldReturnCreatedStatus() {
        RegisterRequest registerRequest = new RegisterRequest(
                "test@example.com",
                "password",
                "test",
                "testov"
        );

        when(authService.register(registerRequest)).thenReturn(true);

        ResponseEntity<?> response = authController.register(registerRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void register_missingEmailOrPassword_shouldReturnBadRequestStatus() {
        RegisterRequest registerRequest = new RegisterRequest(
                "",
                "password",
                "test",
                "testov"
        );

        ResponseEntity<?> response = authController.register(registerRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void register_authServiceUnavailable_shouldReturnServiceUnavailableStatus() {
        RegisterRequest registerRequest = new RegisterRequest(
                "test@example.com",
                "password",
                "test",
                "testov"
        );

        when(authService.register(registerRequest)).thenReturn(false);

        ResponseEntity<?> response = authController.register(registerRequest);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    }

    @Test
    void getNewAccessToken_validRequest_shouldReturnAccessToken() throws AuthException {
        var refreshJwtRequest = new RefreshJwtRequest("refreshToken", "accessToken");
        var expectedResponse = new NewAccessTokenResponse("newAccessToken");

        when(authService.getAccessToken(refreshJwtRequest.getRefreshToken(), refreshJwtRequest.getAccessToken()))
                .thenReturn(expectedResponse);

        ResponseEntity<?> response = authController.getNewAccessToken(refreshJwtRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getNewRefreshToken_validRequest_shouldReturnRefreshToken() {
        NewRefreshJwtRequest newRefreshJwtRequest = new NewRefreshJwtRequest("refreshToken");
        var expectedResponse = new NewRefreshTokenResponse("newRefreshToken");

        when(authService.getNewRefreshToken(newRefreshJwtRequest.getRefreshToken())).thenReturn(expectedResponse);

        ResponseEntity<?> response = authController.getNewRefreshToken(newRefreshJwtRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getNewRefreshToken_invalidRequest_shouldReturnUnauthorizedStatus() {
        NewRefreshJwtRequest newRefreshJwtRequest = new NewRefreshJwtRequest("invalidToken");

        when(authService.getNewRefreshToken(newRefreshJwtRequest.getRefreshToken())).thenReturn(null);

        ResponseEntity<?> response = authController.getNewRefreshToken(newRefreshJwtRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Не действительный токен повторного обновления", response.getBody());
    }
}