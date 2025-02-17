package com.unir.roleapp.controller;

import com.unir.roleapp.dto.UserDTO;
import com.unir.roleapp.entity.User;
import com.unir.roleapp.error.ErrorResponse;
import com.unir.roleapp.repository.UserRepository;
import com.unir.roleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired private UserService userService;


    // BUSCAR USAUARIO POR ID
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    // INICIAR SESIÓN
    @GetMapping("/login")
    public ResponseEntity<?> getUserByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        UserDTO user = userService.getUserByEmail(email, password);
        return ResponseEntity.ok(user);
    }


    // AÑADIR UN NUEVO USUARIO
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        UserDTO userDto = userService.save(userDTO);
        return ResponseEntity
                .created(URI.create("/api/user/" + userDto.getId()))
                .body(userDto);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> postUser(@RequestBody UserDTO userDTO) {
        UserDTO userDto = userService.update( userDTO);
        return ResponseEntity
                .created(URI.create("/api/user/" + userDto.getId()))
                .body(userDto);
    }

    // ELIMINAR CUENTA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
