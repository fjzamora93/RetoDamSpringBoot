package com.unir.roleapp.controller;


import com.unir.roleapp.dto.CharacterRequestDTO;
import com.unir.roleapp.dto.CharacterResponseDTO;
import com.unir.roleapp.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    @Autowired private CharacterService characterService;

    // Endpoint para obtener todos los personajes
    @GetMapping
    public ResponseEntity<List<CharacterResponseDTO>> getAllCharacters() {
        List<CharacterResponseDTO> characterList =  characterService.getAllCharacters();
        return ResponseEntity.ok(characterList);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CharacterResponseDTO>> getCharactersByUser(@PathVariable Long userId) {
        List<CharacterResponseDTO> characterList = characterService.getCharactersByUser(userId);
        return ResponseEntity.ok(characterList);
    }


    // Endpoint para obtener un personaje por su ID
    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponseDTO> getCharacterById(@PathVariable Long id) {
        CharacterResponseDTO character = characterService.getCharacterById(id);
        return ResponseEntity.ok(character);
    }

    // Endpoint para crear un nuevo personaje o actualizarlo
    @PostMapping
    public ResponseEntity<CharacterResponseDTO> postCharacter(@RequestBody CharacterRequestDTO character) {
        CharacterResponseDTO createdCharacter = characterService.saveOrUpdateCharacter(character);
        return ResponseEntity
                .created(URI.create("/api/characters/" + createdCharacter.getId()))
                .body(createdCharacter);
    }

    // Endpoint para eliminar un personaje por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        characterService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }


}
