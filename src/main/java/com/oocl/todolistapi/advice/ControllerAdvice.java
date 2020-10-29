package com.oocl.todolistapi.advice;

import com.oocl.todolistapi.exception.TodoNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleTodoNotFoundException(Exception e) {
        return new ErrorResponse("NOT_FOUND", e.getMessage(), NOT_FOUND.value(), now());
    }
}
