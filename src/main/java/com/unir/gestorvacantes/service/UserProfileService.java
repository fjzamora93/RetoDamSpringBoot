package com.unir.gestorvacantes.service;
import com.unir.auth.model.User;
import com.unir.gestorvacantes.dto.UserProfileDTO;
import com.unir.gestorvacantes.model.UserProfile;
import com.unir.gestorvacantes.repository.UserProfileRepository;
import com.unir.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    // Obtener todos los perfiles de usuario
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    // Eliminar un perfil de usuario
    public void deleteUserProfile(Long id) {

        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserProfile not found"));
        userProfileRepository.delete(userProfile);
    }

    // Upsert: Insertar o actualizar un perfil de usuario
    public UserProfile upsertUserProfile(UserProfileDTO userProfileDTO) {
        System.out.println("Petiicon de añadir nuevo UsuarioProfile" +userProfileDTO.toString() );

        User user = userRepository.findById(userProfileDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserProfile not found"));

        UserProfile userProfile = new UserProfile(
                userProfileDTO.getName(),
                user
        );

        return userProfileRepository.save(userProfile);
    }
}