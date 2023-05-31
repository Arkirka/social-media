package ru.vorobyov.socialmediaapi.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vorobyov.socialmediaapi.dto.exception.ApiErrorResponse;

import java.util.Date;

/**
 * The type Exception handler controller.
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * Handle method argument not valid api error response.
     *
     * @return the api error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse handleMethodArgumentNotValid() {
        return new ApiErrorResponse(
                "Некорректные параметры запроса",
                HttpStatus.BAD_REQUEST.value(),
                new Date()
        );
    }

    /**
     * Handle entity not found api error response.
     *
     * @return the api error response
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiErrorResponse handleEntityNotFound() {
        return new ApiErrorResponse(
                "Запись не найдена",
                HttpStatus.NOT_FOUND.value(),
                new Date()
        );
    }

    /**
     * Handle unauthorized api error response.
     *
     * @param e the exception
     * @return the api error response
     */
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiErrorResponse handleUnauthorized(AuthException e) {
        return new ApiErrorResponse(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                new Date()
        );
    }
}
