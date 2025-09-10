package com.todolist.security;


import com.todolist.exceptions.InvalidBearerTokenException;
import com.todolist.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/auth/")
                || requestURI.startsWith("/swagger-ui")
                || requestURI.startsWith("/v3/api-doc")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getToken(request);

        if(Objects.isNull(token)){
            throw new InsufficientAuthenticationException("The bearer token is missing!");
        }

        String userLogin = tokenService.validateToken(token);

        if(Objects.isNull(userLogin)){
            throw new InvalidBearerTokenException("There is a error in your bearer token, it can be Expired or is not correct!");
        }

        UserDetails userDetails = userRepository.findByLogin(userLogin);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);


    }

    public String getToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if(Objects.isNull(authorization) || !authorization.startsWith("Bearer ")){
            return null;
        }
        return authorization.replace("Bearer ", "");
    }

}
