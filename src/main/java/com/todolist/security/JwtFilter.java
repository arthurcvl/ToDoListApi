package com.todolist.security;


import com.todolist.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if(requestURI.equals("/auth/register") || requestURI.equals("/auth/login")){
            doFilter(request, response, filterChain);
            return;
        }


        String token = getToken(request);

        if(token != null){
            String userLogin = tokenService.validateToken(token);
            UserDetails userDetails = userRepository.findByLogin(userLogin);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        doFilter(request, response, filterChain);


    }

    public String getToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        return authorization.replace("Bearer ", "");
    }

}
