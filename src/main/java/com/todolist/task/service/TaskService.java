package com.todolist.task.service;

import com.todolist.exceptions.BadRequestException;
import com.todolist.task.enums.TaskState;
import com.todolist.task.model.Task;
import com.todolist.task.mapper.TaskMapper;
import com.todolist.task.dto.TaskPostRequestBody;
import com.todolist.task.dto.TaskPutRequestBody;
import com.todolist.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    //TODO -> Agora ficou faltando praticamente so os testes e etc
    //TODO -> sera que eu devo passar o id ra string e usar generation strategy=UUID ?
    //TODO ->  adicionar @valid no controller e as notações @NotNull etc nos DTOs

    //TODO -> Criar em todos os layers um findByName e etc

    private final TaskRepository taskRepository;

    //TODO -> Acho que se o pageable for null, implementar um sort padrao pro projeto idk, prob esse sort tem que ser
    // com base na prioridade

    //TODO -> na real acho melhor criar uma logica pra listar com base em varios tipos de atributos, porque nesse
    //caso especifico acho que é bem importante, apesar do pageable ja permitir sort

    public Page<Task> getAll(Pageable pageable){
        return taskRepository.findAll(pageable);
    }

    public Task findByIdOrThrowBadRequestException(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Task not found!"));
    }

    public Task save(TaskPostRequestBody taskPostRequestBody){
        Task task = TaskMapper.INSTANCE.toTask(taskPostRequestBody);
        task.setTaskState(TaskState.TODO);
        return taskRepository.save(task);
    }

    //Depois pesquisar mais sobre essa "boa" pratica
    public void update(TaskPutRequestBody taskPutRequestBody){
        Task oldTask = findByIdOrThrowBadRequestException(taskPutRequestBody.getId());
        Task taskTobeSaved = TaskMapper.INSTANCE.toTask(taskPutRequestBody);
        taskTobeSaved.setId(oldTask.getId());
        taskRepository.save(taskTobeSaved);
    }

    public void delete(Long id){
        taskRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public List<Task> getByTaskState(TaskState taskState) {
        return taskRepository.findByTaskState(taskState);
    }
}
