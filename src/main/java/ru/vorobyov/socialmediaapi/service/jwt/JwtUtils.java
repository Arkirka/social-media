package ru.vorobyov.socialmediaapi.service.jwt;

import ru.vorobyov.socialmediaapi.model.JwtAuthentication;

import java.util.Map;

public class JwtUtils {

    public static JwtAuthentication generate(Map<String, Object> claims) {

        JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setAuthenticated(false);
        jwtAuthentication.setUsername((String) claims.get("username"));
        jwtAuthentication.setFirstName((String) claims.get("firstName"));

        return jwtAuthentication;
    }
}