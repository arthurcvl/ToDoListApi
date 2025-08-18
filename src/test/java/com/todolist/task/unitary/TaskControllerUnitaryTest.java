package com.todolist.task.unitary;


import com.todolist.task.controller.TaskController;
import com.todolist.task.dto.TaskPostRequestBody;
import com.todolist.task.dto.TaskPutRequestBody;
import com.todolist.task.model.Task;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@ExtendWith(SpringExtension.class)
class TaskControllerUnitaryTest {
    @InjectMocks
    TaskController taskController;
    @Mock
    TaskService taskServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Task> page = new PageImpl<>(List.of(TaskCreator.createValidTask()));

        BDDMockito.when(taskServiceMock.getAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);

        BDDMockito.when(taskServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.any(Long.class))).thenReturn(TaskCreator.createValidTask());
        BDDMockito.when(taskServiceMock.save(ArgumentMatchers.any(TaskPostRequestBody.class))).thenReturn(TaskCreator.createValidTask());


        BDDMockito.doNothing().when(taskServiceMock).update(ArgumentMatchers.any(TaskPutRequestBody.class));
        BDDMockito.doNothing().when(taskServiceMock).delete(ArgumentMatchers.any(Long.class));

    }


    @Test
    @DisplayName("list returns a page of task when successful")
    void list_ReturnsPageOfTasks_WhenSuccessful(){
        Task expectedTask = TaskCreator.createValidTask();

        Page<Task> page = taskController.list(Pageable.ofSize(10)).getBody();

        Assertions.assertThat(page).isNotNull();

        Assertions.assertThat(page.toList()).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(page.toList().getFirst().getId()).isEqualTo(expectedTask.getId());

    }


    @Test
    @DisplayName("findById returns Task successful when passing a valid id")
    void findById_ReturnsTask_WhenSuccessful(){
        Task expectedTask = TaskCreator.createValidTask();

        Task task = taskController.findById(2L).getBody();

        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isEqualTo(expectedTask.getId());

    }

    @Test
    @DisplayName("save returns Task when successful")
    void save_ReturnsTask_WhenSuccessful(){
        Task excepectedTask = TaskCreator.createValidTask();

        Task task = taskController.save(TaskPostRequestBodyCreator.createValidTaskPostRequestBody()).getBody();

        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isEqualTo(excepectedTask.getId());
    }

    @Test
    @DisplayName("replace returns Void ResponseEntity and not throw BadRequestException when Successful")
    void replace_ReturnsVoidResponseEntity_WhenSuccessful(){

        Assertions.assertThatCode(() -> taskController.replace(TaskPutRequestBodyCreator.createValidTaskPutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = taskController.replace(TaskPutRequestBodyCreator.createValidTaskPutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete returns Void ResponseEntity and not throw BadRequestException when Successful")
    void delete_ReturnsNothing_WhenSuccessful(){

        Assertions.assertThatCode(() -> taskController.delete(10L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = taskController.delete(10L);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


    }















}
