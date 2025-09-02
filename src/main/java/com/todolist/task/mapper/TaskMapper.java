package com.todolist.task.mapper;


import com.todolist.task.dto.TaskDetailsDto;
import com.todolist.task.model.Task;
import com.todolist.task.dto.TaskPostRequestBody;
import com.todolist.task.dto.TaskPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class TaskMapper {

    public static final TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    public abstract Task toTask(TaskPostRequestBody taskPostRequestBody);
    public abstract Task toTask(TaskPutRequestBody taskPutRequestBody);
    public abstract TaskDetailsDto toTaskDetailsDto(Task task);

}
