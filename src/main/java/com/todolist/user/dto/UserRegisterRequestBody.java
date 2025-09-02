package com.todolist.user.dto;

import com.todolist.user.enums.UserRole;

public record UserRegisterRequestBody(String login, String password, UserRole userRole) {
}
