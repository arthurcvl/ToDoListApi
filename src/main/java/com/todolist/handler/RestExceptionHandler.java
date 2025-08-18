package com.todolist.handler;


import com.todolist.exceptions.BadRequestException;
import com.todolist.exceptions.details.BadRequestExceptionDetails;
import com.todolist.exceptions.details.ValidationExceptionDetails;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<BadRequestExceptionDetails> badRequestExceptionRestHandler(BadRequestException bre){
        return new ResponseEntity<>(BadRequestExceptionDetails.builder()
                .name("Bad Request Exception")
                .message(bre.getMessage())
                .time(LocalDateTime.now())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationExceptionDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        return new ResponseEntity<>(ValidationExceptionDetails.builder()
                .name("Bad Request Exception")
                .message("You commited a mistake a mistake by passing an invalid argument or" +
                        "not passing an necessary argument")
                .time(LocalDateTime.now())
                .fields(fields)
                .fieldsMessage(fieldsMessage)
                .build(), HttpStatus.BAD_REQUEST);

    }
}
