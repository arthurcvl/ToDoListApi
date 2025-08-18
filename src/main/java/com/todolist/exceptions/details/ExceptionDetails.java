package com.todolist.exceptions.details;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected String name;
    protected String message;
    protected LocalDateTime time;
}
