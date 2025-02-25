package com.unir.roleapp.service;

import com.unir.roleapp.dto.UserDTO;
import com.unir.roleapp.entity.User;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import com.unir.roleapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * BUSCAR USUARIO POR ID
     */
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * BUSCAR USUARIO POR EMAIL
     */
    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * VALIDAR CREDENCIALES
     */
    public boolean validateCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * GUARDAR USUARIO (ApiUser)
     */
    public UserDTO save(UserDTO apiUser) {
        // Verificar si el email ya está registrado
        if (userRepository.findByEmail(apiUser.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL EMAIL YA ESTÁ REGISTRADO");
        }

        // Encriptar la contraseña
        apiUser.setPassword(passwordEncoder.encode(apiUser.getPassword()));

        // Guardar el nuevo usuario
        User userToSave = modelMapper.map(apiUser, User.class);
        User savedUser = userRepository.save(userToSave);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    /**
     * ACTUALIZAR USUARIO (ApiUser)
     */
    public UserDTO update(UserDTO apiUser) {
        User userFromDb = userRepository.findById(apiUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USUARIO NO ENCONTRADO"));

        // Verificar unicidad del email solo si lo han cambiado
        Optional<User> existingUserByEmail = userRepository.findByEmail(apiUser.getEmail());
        if (existingUserByEmail.isPresent() &&
                !apiUser.getEmail().equalsIgnoreCase(existingUserByEmail.get().getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL EMAIL YA ESTÁ REGISTRADO");
        }

        // Gestionar la contraseña: mantener el hash si no ha sido modificada
        if (!passwordEncoder.matches(apiUser.getPassword(), userFromDb.getPassword())) {
            apiUser.setPassword(passwordEncoder.encode(apiUser.getPassword()));
        } else {
            apiUser.setPassword(userFromDb.getPassword());
        }

        // Actualizar el usuario
        User userToUpdate = modelMapper.map(apiUser, User.class);
        User updatedUser = userRepository.save(userToUpdate);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    /**
     * ELIMINAR USUARIO POR EMAIL
     */
    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        userRepository.delete(user);
    }
}