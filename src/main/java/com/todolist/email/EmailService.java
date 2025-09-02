package com.todolist.email;

import com.todolist.task.enums.TaskState;
import com.todolist.task.model.Task;
import com.todolist.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final TaskRepository taskRepository;

    public List<Task> getExpiredPendingTasks(){
        List<Task> pendingTasks = taskRepository.findByTaskState(TaskState.TODO);
        LocalDate today = LocalDate.now();

        List<Task> expiredPendingTasks = pendingTasks.stream().filter(task -> task.getExpiresAt().isEqual(today) ||
                        task.getExpiresAt().isAfter(today))
                .toList();

        return expiredPendingTasks;
    }

}
