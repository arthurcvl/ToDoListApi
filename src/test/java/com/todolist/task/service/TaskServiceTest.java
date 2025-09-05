package com.todolist.task.service;

import com.todolist.exceptions.BadRequestException;
import com.todolist.task.dto.TaskDetailsDto;
import com.todolist.task.dto.TaskPostRequestBody;
import com.todolist.task.dto.TaskPutRequestBody;
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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    private PageImpl<Task> tasksPage;
    private final User user = User.builder()
            .id(1L)
            .login("arthur")
            .build();

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

        t.setUser(user);
        t2.setUser(user);

         tasksPage = new PageImpl<>(List.of(t, t2));


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetAllSuccess() {
        //Given
        BDDMockito.given(taskRepository.findByUser(ArgumentMatchers.eq(user), ArgumentMatchers.any(Pageable.class))).willReturn(tasksPage);

        //When
        Page<TaskDetailsDto> returnedFormattedTasks = taskService.getAll(user, Pageable.ofSize(20));

        //Then
        Assertions.assertThat(returnedFormattedTasks.getSize()).isEqualTo(tasksPage.getSize());
        BDDMockito.verify(taskRepository, Mockito.times(1))
                .findByUser(user, Pageable.ofSize(20));


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
    void testSaveSuccess() {
        //Given
        TaskPostRequestBody task = TaskPostRequestBody.builder()
                .name("Trabalho Escolar 1")
                .description("Trabalho de matematica proposto pela professora X")
                .taskPriority(TaskPriority.HIGH)
                .build();

        Task taskRelatedToRepo = Task.builder()
                .name("Trabalho Escolar 1")
                .description("Trabalho de matematica proposto pela professora X")
                .taskState(TaskState.TODO)
                .taskPriority(TaskPriority.HIGH)
                .user(user)
                .build();

        BDDMockito.given(taskRepository.save(taskRelatedToRepo)).willReturn(taskRelatedToRepo);

        //When
        TaskDetailsDto returnedTask = taskService.save(user, task);

        //Then
        Assertions.assertThat(returnedTask.id()).isEqualTo(taskRelatedToRepo.getId());
        Assertions.assertThat(returnedTask.name()).isEqualTo(taskRelatedToRepo.getName());
        Assertions.assertThat(returnedTask.taskState()).isEqualTo(taskRelatedToRepo.getTaskState());
        BDDMockito.verify(taskRepository, Mockito.times(1)).save(taskRelatedToRepo);


    }

    @Test
    void testUpdateSuccess() {
        //Given
        TaskPutRequestBody taskPutRequestBody = TaskPutRequestBody.builder()
                .id(1L)
                .name("Trabalho Escolar 1")
                .description("Trabalho de matematica proposto pela professora X")
                .taskState(TaskState.DONE)
                .taskPriority(TaskPriority.HIGH)
                .build();
        Task oldTask = Task.builder()
                .id(1L)
                .name("Trabalho Escolar 1")
                .description("Trabalho de matematica proposto pela professora X")
                .taskState(TaskState.TODO)
                .taskPriority(TaskPriority.HIGH)
                .user(user)
                .build();


        BDDMockito.given(taskRepository.findByUserAndId(user, 1L)).willReturn(Optional.of(oldTask));
        //When
        taskService.update(user, taskPutRequestBody);

        //Then
        ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
        BDDMockito.verify(taskRepository, BDDMockito.times(1)).save(taskArgumentCaptor.capture());
        BDDMockito.verify(taskRepository, BDDMockito.times(1)).findByUserAndId(user, 1L);

        Task savedTask = taskArgumentCaptor.getValue();
        Assertions.assertThat(savedTask.getId()).isEqualTo(oldTask.getId());
        Assertions.assertThat(savedTask.getUser().getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(savedTask.getName()).isEqualTo(taskPutRequestBody.getName());
        Assertions.assertThat(savedTask.getTaskState()).isEqualTo(taskPutRequestBody.getTaskState());
    }

    @Test
    void testUpdateNotFound() {
        //Given
        TaskPutRequestBody taskPutRequestBody = TaskPutRequestBody.builder()
                .id(1L)
                .name("Trabalho Escolar 1")
                .description("Trabalho de matematica proposto pela professora X")
                .taskState(TaskState.DONE)
                .taskPriority(TaskPriority.HIGH)
                .build();
        BDDMockito.given(taskRepository.findByUserAndId(user, 1L)).willReturn(Optional.empty());

        //When
        Throwable thrown = Assertions.catchThrowable(() -> taskService.update(user, taskPutRequestBody));

        //Then
        Assertions.assertThat(thrown).isInstanceOf(BadRequestException.class).hasMessage("This user task was not found!");
    }

    @Test
    void testDeleteSuccess() {
        //Given
        Task task = Task.builder()
                .id(1L)
                .name("Trabalho Escolar 1")
                .description("Trabalho de matematica proposto pela professora X")
                .taskState(TaskState.TODO)
                .taskPriority(TaskPriority.HIGH)
                .user(user)
                .build();

        BDDMockito.given(taskRepository.findByUserAndId(user, 1L)).willReturn(Optional.of(task));
        BDDMockito.doNothing().when(taskRepository).delete(task);

        //When
        taskService.delete(user, 1L);

        //Then
        BDDMockito.verify(taskRepository, Mockito.times(1)).findByUserAndId(user, 1L);
        BDDMockito.verify(taskRepository, Mockito.times(1)).delete(task);

    }

    @Test
    void testDeleteNotFound() {
        //TODO -> verificar a logica desse teste principalmente pra ver se o taskRepo.findByUserAndId vai ser chamado de dentro do paramtro de taskRepo.delete

        //Given
        Task task = Task.builder()
                .id(1L)
                .name("Trabalho Escolar 1")
                .description("Trabalho de matematica proposto pela professora X")
                .taskState(TaskState.TODO)
                .taskPriority(TaskPriority.HIGH)
                .user(user)
                .build();

        BDDMockito.given(taskRepository.findByUserAndId(user, 1L)).willReturn(Optional.empty());

        //When
        Throwable thrown = Assertions.catchThrowable(() -> taskService.delete(user, 1L));

        //Then
        Assertions.assertThat(thrown).isInstanceOf(BadRequestException.class).hasMessage("This user task was not found!");

    }

    @Test
    void getByTaskState() {
        //in this scenario i am testing the TaskState todo

        //Given

        List<Task> list = tasksPage.stream().filter(task -> task.getTaskState().equals(TaskState.TODO)).toList();
        PageImpl<Task> filteredTasks = new PageImpl<>(list, Pageable.ofSize(20), list.size());

        BDDMockito.given(taskRepository.findByUserAndTaskState(user, TaskState.TODO, Pageable.ofSize(20)))
                .willReturn(filteredTasks);

        //When
        Page<TaskDetailsDto> byTaskState = taskService.getByTaskState(user, TaskState.TODO, Pageable.ofSize(20));

        //Then
        Assertions.assertThat(byTaskState.getSize()).isEqualTo(filteredTasks.getSize());
        Assertions.assertThat(byTaskState.getContent().getFirst().name()).isEqualTo(filteredTasks.getContent().getFirst().getName());
        Assertions.assertThat(byTaskState.getContent().getFirst().id()).isEqualTo(filteredTasks.getContent().getFirst().getId());
        BDDMockito.verify(taskRepository, BDDMockito.times(1))
                .findByUserAndTaskState(user, TaskState.TODO, Pageable.ofSize(20));
    }
}