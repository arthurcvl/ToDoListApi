package com.todolist.task.service;

import com.todolist.exceptions.BadRequestException;
import com.todolist.task.dto.TaskDetailsDto;
import com.todolist.task.enums.TaskState;
import com.todolist.task.model.Task;
import com.todolist.task.mapper.TaskMapper;
import com.todolist.task.dto.TaskPostRequestBody;
import com.todolist.task.dto.TaskPutRequestBody;
import com.todolist.task.repository.TaskRepository;
import com.todolist.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;


    public Page<TaskDetailsDto> getAll(User user, Pageable pageable){
        return taskRepository.findByUser(user, pageable)
                .map(TaskMapper.INSTANCE::toTaskDetailsDto);
    }

    public TaskDetailsDto findTaskById(User user, Long id){
        Task task = taskRepository.findByUserAndId(user, id)
                .orElseThrow(() -> new BadRequestException("This user task was not found!"));
        return TaskMapper.INSTANCE.toTaskDetailsDto(task);
    }

    //This is used only for verification inside methods of the TaskService class
    private Task findByIdOrThrowBadRequestException(User user, Long id){
         return taskRepository.findByUserAndId(user, id)
                .orElseThrow(() -> new BadRequestException("This user task was not found!"));

    }

    public TaskDetailsDto save(User user, TaskPostRequestBody taskPostRequestBody){
        Task task = TaskMapper.INSTANCE.toTask(taskPostRequestBody);
        task.setTaskState(TaskState.TODO);
        task.setUser(user);
        return TaskMapper.INSTANCE.toTaskDetailsDto(taskRepository.save(task));
    }

    //Depois pesquisar mais sobre essa "boa" pratica
    public void update(User user, TaskPutRequestBody taskPutRequestBody){
        //With this i can guarantee that the user in SecurityContextHolder and user associated with the Task are the same
        Task oldTask = findByIdOrThrowBadRequestException(user, taskPutRequestBody.getId());

        Task taskTobeSaved = TaskMapper.INSTANCE.toTask(taskPutRequestBody);
        taskTobeSaved.setId(oldTask.getId());
        taskTobeSaved.setUser(user);
        taskRepository.save(taskTobeSaved);
        //This should work? idk
    }


    public void delete(User user, Long id){
        taskRepository.delete(findByIdOrThrowBadRequestException(user, id));
    }

    public Page<TaskDetailsDto> getByTaskState(User user, TaskState taskState, Pageable pageable) {
        return taskRepository.findByUserAndTaskState(user, taskState, pageable)
                .map(TaskMapper.INSTANCE::toTaskDetailsDto);
    }
}
