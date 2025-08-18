package com.todolist.task.dto;

import com.todolist.task.enums.TaskPriority;
import com.todolist.task.enums.TaskState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskPutRequestBody {
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskState taskState;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private TaskPriority taskPriority;
}
