package com.todolist.task.enums;

import lombok.Getter;

@Getter
public enum TaskState {

    //TODO -> IDK HOW THESE STRINGS SHOULD, LATER IN THE PROJECT I WILL THINK ABOUT THIS
    DONE("Done"),
    TODO("To-Do");

    private final String state;

    TaskState(String state) {
        this.state = state;
    }

}
