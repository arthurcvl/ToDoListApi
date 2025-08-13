package com.todolist.task;

import com.todolist.task.enums.TaskPriority;
import com.todolist.task.enums.TaskState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity()
@Table(name = "tarefas")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private TaskState taskState;

    private TaskPriority taskPriority;

}

