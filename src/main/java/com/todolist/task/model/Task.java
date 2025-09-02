package com.todolist.task.model;

import com.todolist.task.enums.TaskPriority;
import com.todolist.task.enums.TaskState;
import com.todolist.user.model.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity()
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "task_name")
    private String name;

    @Column(name = "task_description")
    private String description;

    @Column(name = "task_state")
    @Enumerated(EnumType.STRING)
    private TaskState taskState;

    @Column(name = "task_priority")
    @Enumerated(EnumType.ORDINAL)
    private TaskPriority taskPriority;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "expires_at")
    private LocalDate expiresAt;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;


}

