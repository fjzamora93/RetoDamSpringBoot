package com.unir.roleapp.controller;
import com.unir.roleapp.SecurityConfig.JwtTokenProvider;
import com.unir.roleapp.dto.UserDTO;
import com.unir.roleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@AuthenticationPrincipal String email, @RequestBody UserDTO user) {
        user.setEmail(email);
        UserDTO updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser() {
        return ResponseEntity.ok().build();
    }
}
