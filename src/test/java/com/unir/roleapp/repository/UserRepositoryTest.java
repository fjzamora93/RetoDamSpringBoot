package com.unir.roleapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.unir.roleapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    // Crear un usuario para las pruebas y lo guardamos en el repositorio en memoria local
    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        userRepository.save(user);
    }

    // Buscar por ID
    @Test
    public void testFindById() {
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertTrue(foundUser.isPresent(), "Usuario debería ser encontrado");
        assertEquals(user.getEmail(), foundUser.get().getEmail(), "El email del usuario debe coincidir");
    }

    // Buscar por email
    @Test
    public void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        assertTrue(foundUser.isPresent(), "Usuario debería ser encontrado");
        assertEquals(user.getEmail(), foundUser.get().getEmail(), "El email del usuario debe coincidir");
    }

    @Test
    public void testFindByEmailNotFound() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");
        assertFalse(foundUser.isPresent(), "Usuario no debería ser encontrado");
    }


    // Buscar por email y contraseña
    @Test
    public void testFindByEmailAndPassword() {
        Optional<User> foundUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertTrue(foundUser.isPresent(), "Usuario debería ser encontrado");
        assertEquals(user.getEmail(), foundUser.get().getEmail(), "El email del usuario debe coincidir");
        assertEquals(user.getPassword(), foundUser.get().getPassword(), "La contraseña debe coincidir");
    }

    // Buscar con credenciales incorrectas
    @Test
    public void testFindByEmailAndPasswordNotFound() {
        Optional<User> foundUser = userRepository.findByEmailAndPassword("wrong@example.com", "wrongpassword");
        assertFalse(foundUser.isPresent(), "Usuario no debería ser encontrado");
    }
}
