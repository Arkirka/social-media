package ru.vorobyov.socialmediaapi.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vorobyov.socialmediaapi.dto.exception.ApiErrorResponse;

import java.util.Date;

@RestControllerAdvice
public class ExceptionHandlerController {

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
}
