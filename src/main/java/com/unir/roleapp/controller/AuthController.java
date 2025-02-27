package com.unir.roleapp.controller;

import com.unir.roleapp.SecurityConfig.JwtTokenProvider;
import com.unir.roleapp.auth.LoginRequest;
import com.unir.roleapp.auth.LoginResponse;
import com.unir.roleapp.dto.UserDTO;
import com.unir.roleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Generar token JWT
            String token = jwtTokenProvider.createToken(authentication.getName());

            // Devolver respuesta con el token
            return ResponseEntity.ok(new LoginResponse(loginRequest.getEmail(), token, jwtTokenProvider.getExpirationDateFromToken(token)));

        } catch (BadCredentialsException e) {
            // Credenciales inválidas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Credenciales inválidas"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO newUser) {
        try {

            // Encriptar la contraseña
            String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());
            newUser.setPassword(encryptedPassword);

            // Guardar el nuevo usuario
            userService.save(newUser);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuario registrado exitosamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }


}
