package com.todolist.exceptions.details;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

//I am not sure why this specificaly this getter
@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails{
    protected String fields;
    protected String fieldsMessage;
}
