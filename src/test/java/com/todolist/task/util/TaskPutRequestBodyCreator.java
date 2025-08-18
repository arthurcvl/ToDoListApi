package com.todolist.task.util;

import com.todolist.task.dto.TaskPostRequestBody;
import com.todolist.task.dto.TaskPutRequestBody;
import com.todolist.task.enums.TaskPriority;
import com.todolist.task.enums.TaskState;

public class TaskPutRequestBodyCreator {
    public static TaskPutRequestBody createValidTaskPutRequestBody(){
        return TaskPutRequestBody.builder()
                .name("Trabalho Escola")
                .id(1L)
                .description("Proposto pelo professor X")
                .taskState(TaskState.DONE)
                .taskPriority(TaskPriority.HIGH)
                .build();
    }
}
