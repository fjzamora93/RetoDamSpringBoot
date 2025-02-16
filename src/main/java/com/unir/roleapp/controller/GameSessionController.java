package com.unir.roleapp.controller;
import com.unir.roleapp.dto.GameSessionDTO;
import com.unir.roleapp.dto.UserDTO;
import com.unir.roleapp.entity.User;
import com.unir.roleapp.error.ErrorResponse;
import com.unir.roleapp.repository.UserRepository;
import com.unir.roleapp.service.GameSessionService;
import com.unir.roleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gamesession")
public class GameSessionController {

    @Autowired
    private GameSessionService gameSessionService;

    // CREAR UNA NUEVA SESION
    @PostMapping({"/id"})
    public GameSessionDTO createGameSession(@RequestBody GameSessionDTO gameSessionDTO) {
        return gameSessionService.createGameSession(gameSessionDTO);
    }

    // BUSCAR SESIÓN POR ID
    @GetMapping({"/id"})
    public GameSessionDTO getGameSessionById(@RequestParam("id") Long id) {
        return gameSessionService.findById(id);
    }

    @DeleteMapping({"/id"})
    public ResponseEntity deleteGameSessionById(@RequestParam("id") Long id) {
        if (gameSessionService.removeGameSession(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
