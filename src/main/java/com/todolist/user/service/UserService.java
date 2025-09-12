package com.todolist.user.service;


import com.todolist.exceptions.UserNotFoundException;
import com.todolist.user.dto.UserRegisterRequestBody;
import com.todolist.user.dto.UserResponseDto;
import com.todolist.user.mapper.UserMapper;
import com.todolist.user.model.User;
import com.todolist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }


    public User getUserByUserDetails(UserDetails userDetails) {
        return userRepository.findUserByLogin(userDetails.getUsername())
                .orElseThrow(() ->
                        new UserNotFoundException("There was a error retrieving the user stored inside the bearer token",
                        userDetails.getUsername()));
    }

    public UserResponseDto save(UserRegisterRequestBody userRegisterRequestBody){
        if(userRepository.findUserByLogin(userRegisterRequestBody.getLogin()).isPresent()
                || userRepository.findUserByEmail(userRegisterRequestBody.getEmail()).isPresent()){
            throw new DataIntegrityViolationException("You are trying to create a user that already exist!");
        }

        User user = UserMapper.INSTANCE.toUser(userRegisterRequestBody);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toUserResponseDto(savedUser);
    }
}
