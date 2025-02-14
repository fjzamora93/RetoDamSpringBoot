package com.unir.roleapp.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.unir.roleapp.dto.CharacterResponseDTO;
import com.unir.roleapp.dto.UserDTO;
import com.unir.roleapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class UserControllerTest {

    private MockMvc mockMvc;
    private final List<CharacterResponseDTO>  characterEntities = new ArrayList<>();

    @Mock private UserService userService;
    @InjectMocks private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetUserById() throws Exception {

        UserDTO user = new UserDTO(1L,"nombre ejemplo", "test@example.com", "password123",characterEntities );
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetUserByEmailAndPassword() throws Exception {
        UserDTO user = new UserDTO(1L,"nombre ejemplo", "test@example.com", "password123",characterEntities );
        when(userService.getUserByEmail("test@example.com", "password123")).thenReturn(user);

        mockMvc.perform(get("/api/user/login")
                        .param("email", "test@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetUserByEmailAndPassword_NotFound() throws Exception {
        // Simulamos que no se encuentra el usuario con las credenciales proporcionadas.
        when(userService.getUserByEmail("wrong@example.com", "wrongpassword")).thenReturn(null);

        mockMvc.perform(get("/api/user/login")
                        .param("email", "wrong@example.com")
                        .param("password", "wrongpassword"))
                .andExpect(status().isNotFound())  // o isNotFound() según el comportamiento de la API
                .andExpect(jsonPath("$.error").value("NOT_FOUND"));
    }


    @Test
    void testPostUser() throws Exception {
        UserDTO user = new UserDTO("nombre ejemplo", "test@example.com", "password123" );
        when(userService.saveOrUpdate(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUserById(1L);

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isNoContent());
    }
}
