package com.todolist.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.todolist.user.model.User;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TokenService {

    private final String secret = "test";


    //PASSAR UM USUARIO OU LOGIN
    public String createToken(String login) throws UnsupportedEncodingException{

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("todo-api")
                    .withSubject(login)
                    .withExpiresAt(getExpirationTime())
                    .sign(algorithm);


        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }

    }

    public String validateToken(String token) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try{
            return JWT.require(algorithm)
                    .withIssuer("todo-api")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (ValidationException validationException){
            return "";
        }
    }

    private Date getExpirationTime(){
        LocalDateTime futuroLocal = LocalDateTime.now().plusHours(2);
        return Date.from(futuroLocal.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
    }

}
