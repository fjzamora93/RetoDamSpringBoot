package com.unir.roleapp.service;
import com.unir.roleapp.dto.CharacterResponseDTO;
import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.roleapp.dto.GameSessionDTO;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.entity.CustomItem;
import com.unir.roleapp.entity.GameSession;
import com.unir.roleapp.enumm.ItemCategory;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import com.unir.roleapp.repository.CharacterRepository;
import com.unir.roleapp.repository.GameSessionRepository;
import com.unir.roleapp.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameSessionService {

    @Autowired private GameSessionRepository gameSessionRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ItemRepository itemRepository;
    @Autowired private CharacterRepository characterRepository;
    @Autowired private EntityToDtoMapper entityToDtoMapper;

    // CREAR UNA NUEVA SESIÓN
    public GameSessionDTO createGameSession(GameSessionDTO gameSessionDTO) {
        GameSession newGameSession = gameSessionRepository.save(modelMapper.map(gameSessionDTO, GameSession.class));
        return modelMapper.map(newGameSession, GameSessionDTO.class);
    }

    // DELETE SESSIÓN
    public boolean removeGameSession(Long id) {
        Optional<GameSession> gameSession = gameSessionRepository.findById(id);
        if (gameSession.isPresent()) {
            gameSessionRepository.delete(gameSession.get());
            return true;
        }
        return false;
    }


    // BUSCAR SESIÓN POR ID
    public GameSessionDTO findById(Long id) {
        GameSession gameSession = gameSessionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Character not found"));
        return modelMapper.map(gameSession, GameSessionDTO.class);
    }


    // ADD CHARACTER TO SESSION
    public CharacterResponseDTO addCharacterToGameSession(Long characterId, Long gameSessionId) {
        CharacterEntity character = characterRepository
                .findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        GameSession gameSession = gameSessionRepository
                .findById(gameSessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        character.setGameSession(gameSession);
        CharacterEntity updatedCharacter = characterRepository.save(character);
        return entityToDtoMapper.mapToCharacterResponseDTO(updatedCharacter);
    }



    // ADD ITEM TO SESSION
    public CustomItemDTO addItemToGameSession(Long gameSessionId, Long itemId) {
        CustomItem  customItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        GameSession gameSession = gameSessionRepository
                .findById(gameSessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        customItem.setGameSession(gameSession);
        CustomItem updatedItem = itemRepository.save(customItem);

        return modelMapper.map(updatedItem, CustomItemDTO.class);

    }

}
