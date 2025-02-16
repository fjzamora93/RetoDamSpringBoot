package com.unir.roleapp.controller;


import com.unir.roleapp.dto.CharacterRequestDTO;
import com.unir.roleapp.dto.CharacterResponseDTO;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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


    @GetMapping("/user/{userId}")
    public List<CharacterResponseDTO> getCharactersByUser(@PathVariable Long userId) {
        return characterService.getCharactersByUser(userId);
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

    // Endpoint para crear un nuevo personaje
    @PostMapping
    public ResponseEntity<CharacterResponseDTO> postCharacter(@RequestBody CharacterRequestDTO character) {
        CharacterResponseDTO createdCharacter = characterService.saveOrUpdateCharacter(character);

        // Devolver la respuesta con el código de estado 201 (Creado)
        return ResponseEntity
                .created(URI.create("/api/characters/" + createdCharacter.getId()))
                .body(createdCharacter);
    }

    // Endpoint para eliminar un personaje por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        characterService.deleteCharacter(id);
        return ResponseEntity.noContent().build();  // Responde con 204 No Content si la eliminación fue exitosa
    }


}
