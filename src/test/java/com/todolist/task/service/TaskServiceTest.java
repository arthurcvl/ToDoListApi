package com.todolist.task.service;

import com.todolist.exceptions.BadRequestException;
import com.todolist.task.dto.TaskDetailsDto;
import com.todolist.task.enums.TaskPriority;
import com.todolist.task.enums.TaskState;
import com.todolist.task.model.Task;
import com.todolist.task.repository.TaskRepository;
import com.todolist.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    private PageImpl<Task> tasks;

    @BeforeEach
    void setUp() {
        Task t = Task.builder()
                .id(1L)
                .name("Trabalho Escolar 1")
                .description("Trabalho de matematica proposto pela professora X")
                .taskState(TaskState.TODO)
                .taskPriority(TaskPriority.HIGH)
                .createdAt(LocalDate.now())
                .expiresAt(LocalDate.now().plusDays(2))
                .build();

        Task t2 = Task.builder()
                .id(2L)
                .name("Tarefa de Projeto de Pesquisa")
                .description("Estudar as tecnologias necessarias para o Projeto de Pesquisa")
                .taskState(TaskState.TODO)
                .taskPriority(TaskPriority.HIGH)
                .createdAt(LocalDate.now())
                .expiresAt(LocalDate.now().plusDays(10))
                .build();

        User user = User.builder()
                .id(1L)
                .login("arthur")
                .build();

        t.setUser(user);
        t2.setUser(user);

         tasks = new PageImpl<>(List.of(t, t2));


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllSuccess() {
        //Given



    }

    @Test
    void testFindTaskByIdSuccess() {
        //Given. Arrange inputs and targets. Define the behavior of Mock object taskRepository
        Task t = Task.builder()
                .id(1L)
                .name("Trabalho Escolar 1")
                .description("Trabalho de matematica proposto pela professora X")
                .taskState(TaskState.TODO)
                .taskPriority(TaskPriority.HIGH)
                .createdAt(LocalDate.now())
                .expiresAt(LocalDate.now().plusDays(2))
                .build();

        User user = User.builder()
                .id(1L)
                .login("arthur")
                .build();

        t.setUser(user);

        BDDMockito.given(taskRepository.findByUserAndId(user, 1L)).willReturn(Optional.of(t));

        //When. Act on the target behavior. When steps should cover the method to be tested.
        TaskDetailsDto returnedTask = taskService.findTaskById(user, 1L);

        //Then. Assert expected outcome.
        Assertions.assertThat(returnedTask.id()).isEqualTo(t.getId());
        Assertions.assertThat(returnedTask.name()).isEqualTo(t.getName());
        Assertions.assertThat(returnedTask.description()).isEqualTo(t.getDescription());
        Assertions.assertThat(returnedTask.taskState()).isEqualTo(t.getTaskState());
        Assertions.assertThat(returnedTask.taskPriority()).isEqualTo(t.getTaskPriority());
        Assertions.assertThat(returnedTask.expiresAt()).isEqualTo(t.getExpiresAt());
        Mockito.verify(taskRepository, Mockito.times(1)).findByUserAndId(user, 1L);
    }

    @Test
    void testFindTaskByIdNotFound() {
        //Given. Arrange inputs and targets. Define the behavior of Mock object taskRepository

        BDDMockito.given(taskRepository.findByUserAndId(Mockito.any(User.class), Mockito.anyLong())).willReturn(Optional.empty());
        User user = new User();

        //When. Act on the target behavior. When steps should cover the method to be tested.
        Throwable thrown = Assertions.catchThrowable(() -> taskService.findTaskById(user, 1L));

        //Then. Assert expected outcome.
        Assertions.assertThat(thrown).isInstanceOf(BadRequestException.class).hasMessage("This user task was not found!");
        Mockito.verify(taskRepository, Mockito.times(1)).findByUserAndId(user, 1L);
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getByTaskState() {
    }
}