package com.todolist.task.repository;

import com.todolist.task.enums.TaskState;
import com.todolist.task.model.Task;
import com.todolist.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    //METODOS NECESSARIOS PARA O TASK SERVICE
    Page<Task> findByUser(User user, Pageable pageable);
    Optional<Task> findByUserAndId(User user, Long Id);
    //List<Task> findListOfTaskByUser(User user, Pageable pageable);
    Page<Task> findByUserAndTaskState(User user, TaskState taskState, Pageable pageable);

    //METODOS NECESSARIOS PARA O EMAIL SERVICE:
    List<Task> findByTaskState(TaskState taskState);

}
