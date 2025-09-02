package com.todolist.user.service;


import com.todolist.exceptions.UserNotFoundException;
import com.todolist.user.model.User;
import com.todolist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

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
}
