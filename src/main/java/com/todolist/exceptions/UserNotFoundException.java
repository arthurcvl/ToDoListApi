package com.todolist.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private String login;
    public UserNotFoundException(String message, String login) {
        super(message);
        this.login = login;
    }
}
