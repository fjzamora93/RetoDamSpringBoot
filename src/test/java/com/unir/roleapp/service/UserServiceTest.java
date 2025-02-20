package com.unir.roleapp.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.unir.roleapp.dto.CharacterResponseDTO;
import com.unir.roleapp.dto.UserDTO;
import com.unir.roleapp.entity.User;
import com.unir.roleapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() {
        User user = new User(1L,"nombre ejemplo", "test@example.com", "password123" , new ArrayList<>());
        UserDTO userDTO = new UserDTO(1L,"nombre ejemplo", "test@example.com", "password123", new ArrayList<>(), new ArrayList<>() );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }






    @Test
    void testGetUserByEmailAndPassword() {
        // Creación del usuario con la contraseña en texto claro
        User user = new User(1L, "nombre ejemplo", "test@example.com", "$2a$10$7G3GV90jA4pMOrNdzrhMpeQGvIMa/P0d4yIEfrfZEd0fdQ5zjw8ra", new ArrayList<>()); // Contraseña hasheada (esto debería ser generado)
        UserDTO userDTO = new UserDTO(1L, "nombre ejemplo", "test@example.com", "password123", new ArrayList<>(), new ArrayList<>());

        // Simulación de la búsqueda por email
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Simulamos el comportamiento de passwordEncoder.matches()
        when(passwordEncoder.matches("password123", user.getPassword())).thenReturn(true);  // Comprobamos que la contraseña es correcta

        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Llamada al servicio
        UserDTO result = userService.getUserByEmail("test@example.com", "password123");

        // Aserciones
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }


    @Test
    void testGetUserByEmailAndPassword_NotFound() {
        when(userRepository.findByEmail("wrong@example.com"))
                .thenReturn(Optional.empty()); // Simulamos que no se encuentra el usuario

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.getUserByEmail("wrong@example.com", "wrongpassword");
        });

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Usuario o contraseña incorrectos", exception.getReason());
    }



    @Test
    void testSaveOrUpdate_NewUser() {
        UserDTO userDTO = new UserDTO(null, "new@example.com", "password123");
        User user = new User(null, "nombre", "new@example.com", "password123", new ArrayList<>());
        User savedUser = new User(2L, "nombre", "new@example.com", "password123", new ArrayList<>());

        UserDTO savedUserDTO = new UserDTO(2L, "nombre", "new@example.com", "password123",new ArrayList<>(), new ArrayList<>() );

        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(modelMapper.map(savedUser, UserDTO.class)).thenReturn(savedUserDTO);

        UserDTO result = userService.save(userDTO);
        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
    }

    @Test
    void testSaveOrUpdate_ExistingUser() {
        UserDTO userDTO = new UserDTO(null, "existing@example.com", "password123");

        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(new User()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.save(userDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("EL EMAIL YA ESTÁ REGISTRADO", exception.getReason());
    }

    @Test
    void testDeleteUserById_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUserById(1L));
    }

    @Test
    void testDeleteUserById_UserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.deleteUserById(1L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Usuario no encontrado", exception.getReason());
    }
}
