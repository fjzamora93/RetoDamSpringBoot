package com.unir.auth.service;

import com.unir.auth.dto.UserDTO;
import com.unir.auth.model.User;
import com.unir.auth.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * BUSCAR USUARIO POR ID
     */
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(User::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * BUSCAR Todos
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
        }

        return users.stream()
                .map(User::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * BUSCAR USUARIO POR EMAIL
     */
    public UserDetails getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }


    /**
     * GUARDAR USUARIO (ApiUser)
     */
    public UserDTO save(UserDTO userDto) {
        // Verificar si el email ya está registrado
        System.out.println("USUARIO RECIBIDO: " + userDto.toString());
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL EMAIL YA ESTÁ REGISTRADO");
        }

        // Encriptar la contraseña
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Guardar el nuevo usuario y devolvemos el mismo reconvertido en DTO
        User savedUser = userRepository.save(userDto.toJpaEntity());
        return savedUser.toDTO();
    }

    /**
     * ACTUALIZAR USUARIO (ApiUser)
     */
    public UserDTO update(UserDTO userDto) {
        User userFromDb = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USUARIO NO ENCONTRADO"));

        // Verificar unicidad del email solo si lo han cambiado
        Optional<User> existingUserByEmail = userRepository.findByEmail(userDto.getEmail());
        if (existingUserByEmail.isPresent() &&
                !userDto.getEmail().equalsIgnoreCase(existingUserByEmail.get().getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL EMAIL YA ESTÁ REGISTRADO");
        }

        // Gestionar la contraseña: mantener el hash si no ha sido modificada
        if (!passwordEncoder.matches(userDto.getPassword(), userFromDb.getPassword())) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        } else {
            userDto.setPassword(userFromDb.getPassword());
        }

        // Actualizar el usuario
        User savedUser = userRepository.save(userDto.toJpaEntity());
        return savedUser.toDTO();
    }

    /**
     * ELIMINAR USUARIO POR EMAIL
     */
    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        userRepository.delete(user);
    }

    public void disableUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        user.setEnabled(0); // cambia el flag
        userRepository.save(user);
    }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
    }
}