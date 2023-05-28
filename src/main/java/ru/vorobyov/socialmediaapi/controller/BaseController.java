package ru.vorobyov.socialmediaapi.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.service.database.UserService;

abstract class BaseController {

    private final UserService userService;

    BaseController(UserService userService) {
        this.userService = userService;
    }

    User getCurrentUser() throws EntityNotFoundException{
        String email = getCurrentUserEmail();
        return userService.getByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    ResponseEntity<?> getServiceUnavailableResponse(String message){
        return new ResponseEntity<>(
                message,
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    ResponseEntity<?> getNotFoundResponse(String message){
        return new ResponseEntity<>(
                message,
                HttpStatus.NOT_FOUND
        );
    }

    private String getCurrentUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getPrincipal() instanceof String)
                return (String) authentication.getPrincipal();
        }
        return "";
    }
}
