package com.todolist.user.dto;

import com.todolist.user.enums.UserRole;

public record UserResponseDto(String login, String email, UserRole userRole) {
}
