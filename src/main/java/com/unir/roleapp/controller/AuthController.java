package com.unir.roleapp.controller;

import com.unir.roleapp.SecurityConfig.JwtTokenProvider;
import com.unir.roleapp.auth.LoginRequest;
import com.unir.roleapp.auth.LoginResponse;
import com.unir.roleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // Validación de credenciales (deberías usar BCrypt o similar)
        if (userService.validateCredentials(loginRequest.getEmail(), loginRequest.getPassword())) {
            String token = jwtTokenProvider.createToken(loginRequest.getEmail());
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }
}