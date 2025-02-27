package com.unir.roleapp.controller;
import com.unir.roleapp.dto.UserDTO;
import com.unir.roleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser() {
        UserDTO user = getAuthenticatedUser();
        return ResponseEntity.ok(user);
    }


    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        UserDTO authenticatedUser = getAuthenticatedUser();
        user.setEmail(authenticatedUser.getEmail());
        UserDTO updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser() {
        String email = getAuthenticatedUser().getEmail();
        userService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }


    /** PARA EL LOG OUT ES SUFICIENTE CON QUE EL FRONT ELIMINE EL TOKEN, NO ES NECESARIO HACER NADA EN EL BACKEND, EL TOKEN EXPIARÁ POR SÍ MISMO EN 24 HORAS*/
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser() {
        return ResponseEntity.ok().build();
    }


    /** Método auxiliar para obtener el usuario autenticado desde JWT */
    private UserDTO getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof UserDTO)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }
        return (UserDTO) authentication.getPrincipal();
    }
}
