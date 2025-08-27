package com.todolist.exceptions.details;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails{
    protected String fields;
    protected String fieldsMessage;
}
