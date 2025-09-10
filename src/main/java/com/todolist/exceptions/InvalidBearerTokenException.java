package com.todolist.exceptions;

public class InvalidBearerTokenException extends RuntimeException {
    public InvalidBearerTokenException(String message) {
        super(message);
    }
}
