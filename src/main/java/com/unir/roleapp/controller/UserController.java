package com.unir.roleapp.controller;
import com.unir.roleapp.SecurityConfig.JwtTokenProvider;
import com.unir.roleapp.dto.UserDTO;
import com.unir.roleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // OBTENER USUARIO POR TOKEN
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(@RequestHeader("Authorization") String tokenHeader) {
        String token = extractToken(tokenHeader);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = jwtTokenProvider.getEmailFromToken(token);
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    // AÑADIR UN NUEVO USUARIO
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUpUser(@RequestBody UserDTO user) {
        UserDTO userDto = userService.save(user); //cambiado para que funcione con ApiUser
        return ResponseEntity
                .created(URI.create("/api/user/" + userDto.getId()))
                .body(userDto);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(
            @RequestHeader("Authorization") String tokenHeader,
            @RequestBody UserDTO user) {
        String token = extractToken(tokenHeader);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = jwtTokenProvider.getEmailFromToken(token);
        user.setEmail(email);
        UserDTO updatedUser = userService.update(user); //cambiado para que funcione con ApiUser
        return ResponseEntity.ok(updatedUser);
    }

    // ELIMINAR CUENTA
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String tokenHeader) {
        String token = extractToken(tokenHeader);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = jwtTokenProvider.getEmailFromToken(token);
        userService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(@RequestHeader("Authorization") String tokenHeader){
        // En este caso, no hay que hacer nada en el servidor.
        return ResponseEntity.ok().build();
    }

    private String extractToken(String tokenHeader) {
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }
}