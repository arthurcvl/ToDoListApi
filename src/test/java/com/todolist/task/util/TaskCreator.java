package com.todolist.task.util;

import com.todolist.task.enums.TaskPriority;
import com.todolist.task.enums.TaskState;
import com.todolist.task.model.Task;

public class TaskCreator {


    public static Task createValidTask(){
        return Task.builder()
                .id(1L)
                .name("Trabalho Escola")
                .description("Proposto pelo professor X")
                .taskPriority(TaskPriority.HIGH)
                .taskState(TaskState.DONE)
                .build();
    }
}
