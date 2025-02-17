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

@Service
public class UserService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired private EntityToDtoMapper entityToDtoMapper;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    /** BUSCAR USUARIO POR ID */
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /** BUSCAR USARIO POR EMAIL (LA CONTRASEÑA SE COMPRUEBA EN LA API, NO EN BASE DE DATOS)
     * La búsqueda por email se realiza en dos fases:
     * 1. PRimero se busca por email. Si el email es correcto, se comprueba la contraseña.
     * 2. La contraseña realmente ya está recuperada de la bbdd, pero se verifica si se corresponde con su hash.
     * 3. SI hay coincidencia entre el texto plano y el hash, ahora sí es cuando se envía al cliente.
     *
     * */
    public UserDTO getUserByEmail(String email, String password) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos"));

        // Comprobamos que las contraseñas se corresponden
        if (passwordEncoder.matches(password, user.getPassword())) {
            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }

    }


    public UserDTO save(UserDTO userDTO) {
        // Verificar si el email ya está registrado
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL EMAIL YA ESTÁ REGISTRADO");
        }

        // Encriptar la contraseña
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Guardar el nuevo usuario
        User userToSave = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(userToSave);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserDTO update(UserDTO userDTO) {
        User userFromDb = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USUARIO NO ENCONTRADO"));

        // Verificar unicidad del email solo si lo han cambiado
        Optional<User> existingUserByEmail = userRepository.findByEmail(userDTO.getEmail());
        if (existingUserByEmail.isPresent() &&
        !userDTO.getEmail().equalsIgnoreCase(existingUserByEmail.get().getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL EMAIL YA ESTÁ REGISTRADO");
        }

        // Gestionar la contraseña: mantener el hash si no ha sido modificada
        if (!passwordEncoder.matches(userDTO.getPassword(), userFromDb.getPassword())) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        } else {
            userDTO.setPassword(userFromDb.getPassword());
        }

        // Actualizar el usuario
        User userToUpdate = modelMapper.map(userDTO, User.class);
        User updatedUser = userRepository.save(userToUpdate);
        return modelMapper.map(updatedUser, UserDTO.class);
    }


    /** ELIMINAR USUARIO */
    public void deleteUserById(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }


}
