package com.todolist.task.controller;

import com.todolist.task.dto.TaskDetailsDto;
import com.todolist.task.enums.TaskState;
import com.todolist.task.model.Task;
import com.todolist.task.service.TaskService;
import com.todolist.task.dto.TaskPostRequestBody;
import com.todolist.task.dto.TaskPutRequestBody;
import com.todolist.user.model.User;
import com.todolist.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    private final UserService userService;

    //TODO -> To pensando se devo mudar os endpoints pra ficar mais faceis, ex: pra delete ao inves de ser so um /todo/id

    @GetMapping
    public ResponseEntity<Page<TaskDetailsDto>> list(Pageable pageable, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(taskService.getAll(getUserByUserDetails(userDetails), pageable));
    }

    @GetMapping("/pending")
    public ResponseEntity<Page<TaskDetailsDto>> getPendingTasks(Pageable pageable, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(taskService.getByTaskState(getUserByUserDetails(userDetails), TaskState.TODO, pageable));
    }

    @GetMapping("/done")
    public ResponseEntity<Page<TaskDetailsDto>> getDoneTasks(Pageable pageable, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(taskService.getByTaskState(getUserByUserDetails(userDetails), TaskState.DONE, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDetailsDto> findById(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(taskService.findByIdReturnsFormattedTaskOrThrowBadRequestException(getUserByUserDetails(userDetails), id));
    }

    @PostMapping
    public ResponseEntity<TaskDetailsDto> save(@RequestBody @Valid TaskPostRequestBody taskPostRequestBody,
                                               @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(taskService.save(getUserByUserDetails(userDetails), taskPostRequestBody),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid TaskPutRequestBody taskPutRequestBody,
                                        @AuthenticationPrincipal UserDetails userDetails){
        taskService.update(getUserByUserDetails(userDetails), taskPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails userDetails){
        taskService.delete(getUserByUserDetails(userDetails), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private User getUserByUserDetails(UserDetails userDetails){
        return userService.getUserByUserDetails(userDetails);
    }


}
