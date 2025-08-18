package com.todolist.task.controller;

import com.todolist.task.enums.TaskState;
import com.todolist.task.model.Task;
import com.todolist.task.service.TaskService;
import com.todolist.task.dto.TaskPostRequestBody;
import com.todolist.task.dto.TaskPutRequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    //TODO -> To pensando se devo mudar os endpoints pra ficar mais faceis, ex: pra delete ao inves de ser so um /todo/id

    @GetMapping
    public ResponseEntity<Page<Task>> list(Pageable pageable){
        return ResponseEntity.ok(taskService.getAll(pageable));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Task>> getPendingTasks(){
        return ResponseEntity.ok(taskService.getByTaskState(TaskState.TODO));
    }

    @GetMapping("/done")
    public ResponseEntity<List<Task>> getDoneTasks(){
        return ResponseEntity.ok(taskService.getByTaskState(TaskState.DONE));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<Task> save(@RequestBody @Valid TaskPostRequestBody taskPostRequestBody){
        return new ResponseEntity<>(taskService.save(taskPostRequestBody),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid TaskPutRequestBody taskPutRequestBody){
        taskService.update(taskPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
