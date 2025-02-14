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

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired private UserService userService;


    // BUSCAR USAUARIO POR ID
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // CREAR O ACTUALIZAR USUARIO
    @GetMapping("/login")
    public ResponseEntity<?> getUserByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        UserDTO user = userService.getUserByEmail(email, password);

        if (user == null) {
            return new ResponseEntity<>(new ErrorResponse("NOT_FOUND", "Usuario no encontrado o credenciales incorrectas"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(user);
    }


    // AÑADIR UN NUEVO USUARIO
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> postUser(@RequestBody UserDTO userDTO) {
        UserDTO userDto = userService.saveOrUpdate(userDTO);
        return ResponseEntity.ok(userDto);
    }

    // ELIMINAR CUENTA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
