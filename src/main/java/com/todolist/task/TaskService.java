package com.todolist.task;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Page<Task> getAll(Pageable pageable){
        return taskRepository.findAll(pageable);
    }

    //TODO -> ADICIONAR UM .OrElseThrow BadRequestException etc... por enquanto eu vou deixar retornando um optional msm
    public Optional<Task> findById(Long id){
        return taskRepository.findById(id);
    }

    //TODO -> NO FUTURO VAI RECEBER UM TaskPostRequestBody, fazer o mapeamento pra Task e ai salvar
    public Task save(Task task){
        return taskRepository.save(task);
    }

    //TODO -> NO FUTURO VAI RECEBER UM TaskPostRequestBody, fazer o mapeamento pra Task e ai salvar
    public void update(Task task){
        //TODO -> NO FUTURO VOU FAZER A LOGICA DE USAR O findById etc, por enquanto vou deixar mais simples
        taskRepository.save(task);
    }

    public void delete(Long id){
        //TODO -> No futuro usar delete(findById)
        taskRepository.deleteById(id);
    }

}
