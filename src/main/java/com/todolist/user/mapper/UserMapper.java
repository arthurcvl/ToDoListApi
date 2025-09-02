package com.todolist.user.mapper;


import com.todolist.user.dto.UserRegisterRequestBody;
import com.todolist.user.dto.UserResponseDto;
import com.todolist.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class UserMapper {

    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    public abstract User toUser(UserRegisterRequestBody userRegisterRequestBody);
    public abstract UserResponseDto toUserResponseDto(User user);

}
