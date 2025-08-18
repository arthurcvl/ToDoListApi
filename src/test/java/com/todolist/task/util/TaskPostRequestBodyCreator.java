package com.todolist.task.util;


import com.todolist.task.dto.TaskPostRequestBody;
import com.todolist.task.enums.TaskPriority;
import com.todolist.task.enums.TaskState;
import com.todolist.task.model.Task;

public class TaskPostRequestBodyCreator {
    public static TaskPostRequestBody createValidTaskPostRequestBody(){
        return TaskPostRequestBody.builder()
                .name("Trabalho Escola")
                .description("Proposto pelo professor X")
                .taskPriority(TaskPriority.HIGH)
                .build();
    }
}
