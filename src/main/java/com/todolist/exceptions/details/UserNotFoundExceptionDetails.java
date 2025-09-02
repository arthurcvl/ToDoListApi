package com.todolist.exceptions.details;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public final class UserNotFoundExceptionDetails extends ExceptionDetails{
    private String login;
}
