package ru.vorobyov.socialmediaapi.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vorobyov.socialmediaapi.dto.user.UserDto;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.UserService;

/**
 * Controller with basic methods
 */
abstract class BaseController {

    private final UserService userService;

    /**
     * Instantiates a new Base controller.
     *
     * @param userService the user service
     */
    BaseController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets current user.
     *
     * @return the current user
     * @throws EntityNotFoundException the entity not found exception
     */
    User getCurrentUser() throws EntityNotFoundException{
        return userService.getCurrentUser();
    }

    /**
     * Get service unavailable response entity.
     *
     * @param message the message
     * @return the response entity
     */
    ResponseEntity<?> getServiceUnavailableResponse(String message){
        return new ResponseEntity<>(
                message,
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    /**
     * Get not found response entity.
     *
     * @param message the message
     * @return the response entity
     */
    ResponseEntity<?> getNotFoundResponse(String message){
        return new ResponseEntity<>(
                message,
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Get conflict response entity.
     *
     * @param message the message
     * @return the response entity
     */
    ResponseEntity<?> getConflictResponse(String message){
        return new ResponseEntity<>(
                message,
                HttpStatus.CONFLICT
        );
    }

    /**
     * Parse user to dto object.
     *
     * @param user the user object
     * @return the dto object with user data
     */
    UserDto parseUserToDto(User user){
        return new UserDto(
                user.getId(), user.getEmail(),
                user.getFirstName(), user.getLastName()
        );
    }
}
