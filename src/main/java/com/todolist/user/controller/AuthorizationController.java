package com.todolist.user.controller;


import com.todolist.security.TokenService;
import com.todolist.user.dto.UserLoginRequestBody;
import com.todolist.user.dto.UserRegisterRequestBody;
import com.todolist.user.dto.UserResponseDto;
import com.todolist.user.mapper.UserMapper;
import com.todolist.user.model.User;
import com.todolist.user.repository.UserRepository;
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
public class AuthorizationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody UserLoginRequestBody userLoginRequestBody) throws UnsupportedEncodingException {
        UsernamePasswordAuthenticationToken userToken
                = new UsernamePasswordAuthenticationToken(userLoginRequestBody.login(), userLoginRequestBody.password());
        authenticationManager.authenticate(userToken);

        return ResponseEntity.ok(tokenService.createToken(userLoginRequestBody.login()));
    }

    @PostMapping("/register")
    ResponseEntity<UserResponseDto> register(@RequestBody UserRegisterRequestBody userRegisterRequestBody){
        User user = UserMapper.INSTANCE.toUser(userRegisterRequestBody);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        UserResponseDto userResponseDto = UserMapper.INSTANCE.toUserResponseDto(savedUser);

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }


}
