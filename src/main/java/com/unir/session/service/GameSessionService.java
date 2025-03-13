package com.unir.session.service;
import com.unir.roleapp.dto.CharacterResponseDTO;
import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.session.dto.GameSessionDTO;
import com.unir.roleapp.model.CharacterEntity;
import com.unir.roleapp.model.CustomItem;
import com.unir.session.model.GameSession;
import com.unir.auth.model.User;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import com.unir.roleapp.repository.CharacterRepository;
import com.unir.session.repository.GameSessionRepository;
import com.unir.roleapp.repository.CustomItemRepository;
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
