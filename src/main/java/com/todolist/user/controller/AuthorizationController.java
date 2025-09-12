package com.todolist.user.controller;


import com.todolist.security.TokenService;
import com.todolist.user.dto.UserLoginRequestBody;
import com.todolist.user.dto.UserRegisterRequestBody;
import com.todolist.user.dto.UserResponseDto;
import com.todolist.user.mapper.UserMapper;
import com.todolist.user.model.User;
import com.todolist.user.repository.UserRepository;
import com.todolist.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;


    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody @Valid UserLoginRequestBody userLoginRequestBody) throws UnsupportedEncodingException {
        UsernamePasswordAuthenticationToken userToken
                = new UsernamePasswordAuthenticationToken(userLoginRequestBody.getLogin(), userLoginRequestBody.getPassword());
        authenticationManager.authenticate(userToken);

        return ResponseEntity.ok(tokenService.createToken(userLoginRequestBody.getLogin()));
    }

    @PostMapping("/register")
    ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRegisterRequestBody userRegisterRequestBody){
        return new ResponseEntity<>(userService.save(userRegisterRequestBody), HttpStatus.CREATED);
    }


}
