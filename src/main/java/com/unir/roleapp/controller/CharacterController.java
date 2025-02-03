package com.unir.roleapp.controller;


import com.unir.roleapp.dto.CharacterResponseDTO;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    @Autowired private CharacterService characterService;

    // Endpoint para obtener todos los personajes
    @GetMapping
    public List<CharacterResponseDTO> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    // Endpoint para obtener un personaje por su ID
    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponseDTO> getCharacterById(@PathVariable Long id) {
        Optional<CharacterResponseDTO> character = characterService.getCharacterById(id);
        if (character.isPresent()) {
            return ResponseEntity.ok(character.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
