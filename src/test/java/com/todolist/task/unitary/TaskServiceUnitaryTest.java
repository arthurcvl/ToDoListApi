package com.todolist.task.unitary;


import com.todolist.exceptions.BadRequestException;
import com.todolist.task.dto.TaskPostRequestBody;
import com.todolist.task.model.Task;
import com.todolist.task.repository.TaskRepository;
import com.todolist.task.service.TaskService;
import com.todolist.task.util.TaskCreator;
import com.todolist.task.util.TaskPostRequestBodyCreator;
import com.todolist.task.util.TaskPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
class TaskServiceUnitaryTest {
    @InjectMocks
    TaskService taskService;
    @Mock
    TaskRepository taskRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<Task> page = new PageImpl<>(List.of(TaskCreator.createValidTask()));

        BDDMockito.when(taskRepositoryMock.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);

        BDDMockito.when(taskRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(TaskCreator.createValidTask()));
        BDDMockito.when(taskRepositoryMock.save(ArgumentMatchers.any(Task.class))).thenReturn(TaskCreator.createValidTask());

        BDDMockito.doNothing().when(taskRepositoryMock).delete(ArgumentMatchers.any(Task.class));

    }


    @Test
    @DisplayName("getAll returns a page of task when successful")
    void getAll_ReturnsPageOfTasks_WhenSuccessful(){
        Task expectedTask = TaskCreator.createValidTask();

        Page<Task> page = taskService.getAll(Pageable.ofSize(10));

        Assertions.assertThat(page.toList()).isNotNull()
                .isNotEmpty();

        Assertions.assertThat(page.toList().getFirst().getId()).isEqualTo(expectedTask.getId());

    }


    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns Task successful when passing a valid id")
    void findByIdOrThrowBadRequestException_ReturnsTask_WhenSuccessful(){
        Task expectedTask = TaskCreator.createValidTask();

        Task task = taskService.findByIdOrThrowBadRequestException(2L);

        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isEqualTo(expectedTask.getId());

    }


    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when ID passed is not valid")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenTaskNotFound(){
        BDDMockito.when(taskRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> taskService.findByIdOrThrowBadRequestException(10L));
    }


    //Testa principalmente a conversão do TaskMapper
    //Nao sei se faz sentido testar isso aqui
    @Test
    @DisplayName("save_ReturnsTask_WhenSuccessful")
    void save_ReturnsTask_WhenSuccessful(){
        Task excepectedTask = TaskCreator.createValidTask();

        Task task = taskService.save(TaskPostRequestBodyCreator.createValidTaskPostRequestBody());

        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isEqualTo(excepectedTask.getId());
    }

    @Test
    @DisplayName("update_ReturnsTask_WhenSuccessful")
    void update_ReturnsTask_WhenSuccessful(){
        BDDMockito.when(taskRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(TaskCreator.createValidTask()));

        Assertions.assertThatCode(() -> taskService.update(TaskPutRequestBodyCreator.createValidTaskPutRequestBody()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Delete not throws any exception / returns nothing when successful")
    void delete_ReturnsNothing_WhenSuccessful(){

        Assertions.assertThatCode(() -> taskService.delete(10L))
                .doesNotThrowAnyException();

    }










}
