package com.unir.roleapp.controller;
import com.unir.roleapp.dto.CharacterResponseDTO;
import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.roleapp.dto.GameSessionDTO;
import com.unir.roleapp.service.GameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gamesession")
public class GameSessionController {

    @Autowired
    private GameSessionService gameSessionService;


    // BUSCAR SESIÓN POR ID
    @GetMapping("/{id}")
    public ResponseEntity<GameSessionDTO> getGameSessionById(
            @PathVariable Long id
    ) {
        GameSessionDTO gameSession = gameSessionService.findById(id);
        return ResponseEntity.ok(gameSession);
    }

    // CREAR UNA NUEVA SESION
    @Transactional
    @PostMapping("/create")
    public ResponseEntity<GameSessionDTO> createGameSession(
            @RequestBody GameSessionDTO gameSessionDTO
    ) {
        GameSessionDTO gameSession = gameSessionService.createGameSession(gameSessionDTO);
        return ResponseEntity.ok(gameSession);
    }

    // ELIMINAR SESIÓN
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameSession(
            @PathVariable Long id
    ) {
        gameSessionService.deleteGameSession(id);
        return ResponseEntity.noContent().build();
    }

    // AÑADIR PERSONAJE A LA SESIÓN
    @Transactional
    @PostMapping("/add-character")
    public  ResponseEntity addCharacterToGameSession(
            @RequestParam Long characterId,
            @RequestParam Long gameSessionId
    ) {
        CharacterResponseDTO characterResponseDTO = gameSessionService.addCharacterToGameSession(characterId, gameSessionId);
        return ResponseEntity.ok(characterResponseDTO);
    }

    // AÑADIR OBJETO A LA SESIÓN
    @Transactional
    @PostMapping("/add-item")
    public ResponseEntity<CustomItemDTO> addItemToGameSession(
            @RequestParam Long itemId,
            @RequestParam Long gameSessionId
    ){
        CustomItemDTO itemDTO = gameSessionService.addItemToGameSession(itemId, gameSessionId);
        return ResponseEntity.ok(itemDTO);
    }
}
