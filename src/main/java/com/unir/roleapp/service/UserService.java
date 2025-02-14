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
                .orElse(null);
    }

    /** BUSCAR USARIO POR EMAIL (LA CONTRASEÑA SE COMPRUEBA EN LA API, NO EN BASE DE DATOS)
     * La búsqueda por email se realiza en dos fases:
     * 1. PRimero se busca por email. Si el email es correcto, se comprueba la contraseña.
     * 2. La contraseña realmente ya está recuperada de la bbdd, pero se verifica si se corresponde con su hash.
     * 3. SI hay coincidencia entre el texto plano y el hash, ahora sí es cuando se envía al cliente.
     *
     * */
    public UserDTO getUserByEmail(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Comprobar si la contraseña proporcionada coincide con el hash almacenado
            if (checkPassword(password, user.getPassword())) {
                return modelMapper.map(user, UserDTO.class);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    /** AÑADIR NUEVO USUARIO */
    public UserDTO saveOrUpdate(UserDTO userDTO) {
        Optional<User> existingUserByEmail = userRepository.findByEmail(userDTO.getEmail());

        // Verificar la unicidad del email:
        if (existingUserByEmail.isPresent()) {
            // Si es una actualización, el ID del usuario en la BD debe coincidir con el del DTO.
            if (userDTO.getId() == null || !existingUserByEmail.get().getId().equals(userDTO.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EL EMAIL YA ESTÁ REGISTRADO");
            }
        }

        // Verificar si es una actualización o creación
        if (userDTO.getId() != null) {
            // Es una actualización: obtener el usuario existente para comparar la contraseña
            Optional<User> userFromDbOpt = userRepository.findById(userDTO.getId());
            if (userFromDbOpt.isPresent()) {
                User userFromDb = userFromDbOpt.get();
                // Si la contraseña ingresada coincide con la que ya está en la BD,
                // asumimos que no se modificó y mantenemos el hash.
                if (passwordEncoder.matches(userDTO.getPassword(), userFromDb.getPassword())) {
                    userDTO.setPassword(userFromDb.getPassword());
                } else {
                    // La contraseña fue modificada, así que la encriptamos.
                    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                }
            } else {
                // Si por alguna razón no se encuentra el usuario, procedemos a encriptar la contraseña.
                userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
        } else {
            // Para un nuevo usuario siempre encriptamos la contraseña
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        // Convertir el DTO a entidad, guardar y luego mapear de vuelta a DTO
        User userToSave = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(userToSave);
        return modelMapper.map(savedUser, UserDTO.class);
    }


    /** ELIMINAR USUARIO */
    public void deleteUserById(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    /** CHECK PASSWORD */
    public boolean checkPassword(String rawPassword, String storedHashedPassword) {
        return passwordEncoder.matches(rawPassword, storedHashedPassword);
    }

}
