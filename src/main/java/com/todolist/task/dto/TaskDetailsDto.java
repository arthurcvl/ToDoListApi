package com.todolist.task.dto;

import com.todolist.task.enums.TaskPriority;
import com.todolist.task.enums.TaskState;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskDetailsDto(Long id, String name, String description, TaskState taskState, TaskPriority taskPriority, LocalDate expiresAt) {
}
