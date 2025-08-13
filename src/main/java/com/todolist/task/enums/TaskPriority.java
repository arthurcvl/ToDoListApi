package com.todolist.task.enums;

import lombok.Getter;

@Getter
public enum TaskPriority {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final Integer priority;

    TaskPriority(Integer priority) {
        this.priority = priority;
    }

}
