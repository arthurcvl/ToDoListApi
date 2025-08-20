package com.todolist.task.model;

import com.todolist.task.enums.TaskPriority;
import com.todolist.task.enums.TaskState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity()
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    private String description;

    @Column(name = "task_state")
    @Enumerated(EnumType.STRING)
    private TaskState taskState;

    @Column(name = "task_priority")
    @Enumerated(EnumType.ORDINAL)
    private TaskPriority taskPriority;



}

