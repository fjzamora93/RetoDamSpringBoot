package com.unir.gestorvacantes.controller;

import com.unir.gestorvacantes.dto.UserProfileDTO;
import com.unir.gestorvacantes.model.UserProfile;
import com.unir.gestorvacantes.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userprofile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;



    // OBTENER TODOS LOS PERFILES
    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllUserProfiles() {
        List<UserProfile> userProfiles = userProfileService.getAllUserProfiles();
        return ResponseEntity.ok(userProfiles);
    }

    // AÑADIR PERFIL
    @PostMapping
    public ResponseEntity<UserProfile> upsertUserProfile(@RequestBody UserProfileDTO userProfile) {
        UserProfile savedProfile = userProfileService.upsertUserProfile(userProfile);
        return ResponseEntity.ok(savedProfile);
    }

    // ELIMINAR PERFIL
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(
            @PathVariable Long id
    ) {
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }

}
