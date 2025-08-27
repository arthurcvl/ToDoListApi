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
import com.todolist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    //TODO -> Agora ficou faltando praticamente so os testes e etc
    //TODO -> sera que eu devo passar o id ra string e usar generation strategy=UUID ?
    //TODO ->  adicionar @valid no controller e as notações @NotNull etc nos DTOs

    //TODO -> Criar em todos os layers um findByName e etc

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    //TODO -> Acho que se o pageable for null, implementar um sort padrao pro projeto idk, prob esse sort tem que ser
    // com base na prioridade

    //TODO -> na real acho melhor criar uma logica pra listar com base em varios tipos de atributos, porque nesse
    //caso especifico acho que é bem importante, apesar do pageable ja permitir sort

    private User getUserByUserDetails(UserDetails userDetails){
        return userRepository.findUserByLogin(userDetails.getUsername())
                //TODO -> criar um UserNotFoundException e UserNotFoundExceptionDetails
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }


    public Page<TaskDetailsDto> getAll(UserDetails userDetails, Pageable pageable){
        return taskRepository.findByUser(getUserByUserDetails(userDetails), pageable)
                .map(TaskMapper.INSTANCE::toTaskDetailsDto);
    }

    public TaskDetailsDto findByIdReturnsFormattedTaskOrThrowBadRequestException(UserDetails userDetails, Long id){
        Task task = taskRepository.findByUserAndId(getUserByUserDetails(userDetails), id)
                .orElseThrow(() -> new BadRequestException("This user task was not found!"));
        return TaskMapper.INSTANCE.toTaskDetailsDto(task);
    }

    //This is used only for verification inside methods of the TaskService class
    private Task findByIdOrThrowBadRequestException(UserDetails userDetails, Long id){
         return taskRepository.findByUserAndId(getUserByUserDetails(userDetails), id)
                .orElseThrow(() -> new BadRequestException("This user task was not found!"));

    }

    public TaskDetailsDto save(UserDetails userDetails, TaskPostRequestBody taskPostRequestBody){
        Task task = TaskMapper.INSTANCE.toTask(taskPostRequestBody);
        task.setTaskState(TaskState.TODO);
        task.setUser(getUserByUserDetails(userDetails));
        return TaskMapper.INSTANCE.toTaskDetailsDto(taskRepository.save(task));
    }

    //Depois pesquisar mais sobre essa "boa" pratica
    public void update(UserDetails userDetails, TaskPutRequestBody taskPutRequestBody){
        //With this i can guarantee that the user in SecurityContextHolder and user associated with the Task are the same
        Task oldTask = findByIdOrThrowBadRequestException(userDetails, taskPutRequestBody.getId());

        Task taskTobeSaved = TaskMapper.INSTANCE.toTask(taskPutRequestBody);
        taskTobeSaved.setId(oldTask.getId());
        taskTobeSaved.setUser(getUserByUserDetails(userDetails));
        taskRepository.save(taskTobeSaved);
        //This should work? idk
    }


    public void delete(UserDetails userDetails, Long id){
        taskRepository.delete(findByIdOrThrowBadRequestException(userDetails, id));
    }

    public Page<TaskDetailsDto> getByTaskState(UserDetails userDetails, TaskState taskState, Pageable pageable) {
        return taskRepository.findByUserAndTaskState(getUserByUserDetails(userDetails), taskState, pageable)
                .map(TaskMapper.INSTANCE::toTaskDetailsDto);
    }
}
