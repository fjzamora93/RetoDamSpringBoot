package com.unir.adventure.service;
import com.unir.character.dto.CharacterResponseDTO;
import com.unir.character.dto.CustomItemDTO;
import com.unir.adventure.dto.GameSessionDTO;
import com.unir.character.model.CharacterEntity;
import com.unir.character.model.CustomItem;
import com.unir.adventure.model.GameSession;
import com.unir.auth.model.User;
import com.unir.character.mapper.EntityToDtoMapper;
import com.unir.character.repository.CharacterRepository;
import com.unir.adventure.repository.GameSessionRepository;
import com.unir.character.repository.CustomItemRepository;
import com.unir.auth.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class GameSessionService {

    @Autowired private GameSessionRepository gameSessionRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private CustomItemRepository customItemRepository;
    @Autowired private CharacterRepository characterRepository;
    @Autowired private EntityToDtoMapper entityToDtoMapper;
    @Autowired private UserRepository userRepository;
    // BUSCAR SESIÓN POR ID
    public GameSessionDTO findById(Long id) {
        GameSession gameSession = gameSessionRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found"));
        return modelMapper.map(gameSession, GameSessionDTO.class);
    }

    // CREAR UNA NUEVA SESIÓN
    public GameSessionDTO createGameSession(GameSessionDTO gameSessionDTO) {
        User user = userRepository.findById(gameSessionDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        GameSession savedGameSession = gameSessionRepository.save(new  GameSession(user));
        GameSessionDTO newGameSessionDTO = new GameSessionDTO();
        newGameSessionDTO.setId(savedGameSession.getId());
        newGameSessionDTO.setUserId(user.getId());
        return newGameSessionDTO;
    }


    // DELETE SESSIÓN
    public void  deleteGameSession(Long id) {
        GameSession gameSession = gameSessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found"));

        gameSessionRepository.delete(gameSession);

    }


    // ADD CHARACTER TO SESSION
    public CharacterResponseDTO addCharacterToGameSession(Long characterId, Long gameSessionId) {
        CharacterEntity character = characterRepository
                .findById(characterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found"));

        GameSession gameSession = gameSessionRepository
                .findById(gameSessionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found"));

        character.setGameSession(gameSession);
        CharacterEntity updatedCharacter = characterRepository.save(character);
        return entityToDtoMapper.mapToCharacterResponseDTO(updatedCharacter);
    }



    // ADD ITEM TO SESSION
    public CustomItemDTO addItemToGameSession(Long gameSessionId, Long itemId) {
        CustomItem  customItem = customItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        GameSession gameSession = gameSessionRepository
                .findById(gameSessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        customItem.setGameSession(gameSession);
        CustomItem updatedItem = customItemRepository.save(customItem);

        return modelMapper.map(updatedItem, CustomItemDTO.class);

    }

}
