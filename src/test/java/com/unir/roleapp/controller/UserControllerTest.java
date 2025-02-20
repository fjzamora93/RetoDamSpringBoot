package com.unir.roleapp.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.unir.roleapp.dto.CharacterResponseDTO;
import com.unir.roleapp.dto.GameSessionDTO;
import com.unir.roleapp.dto.UserDTO;
import com.unir.roleapp.entity.GameSession;
import com.unir.roleapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

public class UserControllerTest {

    private MockMvc mockMvc;
    private final List<CharacterResponseDTO>  characterEntities = new ArrayList<>();
    private final List<GameSessionDTO>  gameSessions = new ArrayList<>();

    @Mock private UserService userService;
    @InjectMocks private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetUserById() throws Exception {

        UserDTO user = new UserDTO(1L,"nombre ejemplo", "test@example.com", "password123", characterEntities, gameSessions );
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetUserByEmailAndPassword() throws Exception {
        UserDTO user = new UserDTO(1L,"nombre ejemplo", "test@example.com", "password123",characterEntities, gameSessions );
        when(userService.getUserByEmail("test@example.com", "password123")).thenReturn(user);

        mockMvc.perform(get("/api/user/login")
                        .param("email", "test@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserByEmailAndPassword_Unauthorized() throws Exception {
        // Simulamos que el servicio lanza una excepción UNAUTHORIZED cuando las credenciales son incorrectas
        when(userService.getUserByEmail("wrong@example.com", "wrongpassword"))
                .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos"));

        mockMvc.perform(get("/api/user/login")
                        .param("email", "wrong@example.com")
                        .param("password", "wrongpassword"))
                .andExpect(status().isUnauthorized());  // Ahora esperamos 401 en lugar de 404
    }



    @Test
    void testPostUser() throws Exception {
        UserDTO user = new UserDTO("nombre ejemplo", "test@example.com", "password123" );
        when(userService.save(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUserById(1L);

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isNoContent());
    }
}
