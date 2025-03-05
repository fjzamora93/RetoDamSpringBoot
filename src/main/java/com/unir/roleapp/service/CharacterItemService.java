package com.unir.roleapp.service;

import com.unir.roleapp.dto.CharacterItemResponseDTO;
import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import com.unir.roleapp.model.*;
import com.unir.roleapp.repository.CharacterItemRepository;
import com.unir.roleapp.repository.CharacterRepository;
import com.unir.roleapp.repository.CustomItemRepository;
import com.unir.roleapp.repository.GameSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterItemService {

    // Inyección del repositorio
    @Autowired private  CharacterItemRepository characterItemRepository;
    @Autowired private  CustomItemRepository customItemRepository;
    @Autowired private GameSessionRepository gameSessionRepository;
    @Autowired private CharacterRepository characterRepository;

    // Método para obtener todos los items de un personaje
    public List<CharacterItemResponseDTO> getCustomItemsByCharacter(Long characterId) {
        List<CharacterItem> items = characterItemRepository.findByCharacterId(characterId);
        return items.stream()
                .map(CharacterItemResponseDTO::toDTO)
                .collect(Collectors.toList());
    }

    // Método para eliminar un item de un personaje
    public void deleteItemFromCharacter(Long characterId, Long itemId) {
        characterItemRepository.deleteByCharacterIdAndItemId(characterId, itemId);
    }
    public CharacterItemResponseDTO addOrUpdateItemToCharacter(
            Long characterId,
            CustomItemDTO customItemDTO,
            int quantity
    ) {
        // Buscar la sesión de juego asociada al personaje
        GameSession gameSession = gameSessionRepository
                .findByCharacterId(characterId)
                .orElseThrow(() -> new EntityNotFoundException("Game Session not found"));

        // Buscar si el CustomItem ya existe (si tiene ID)
        CustomItem customItem;
        if (customItemDTO.getId() != null) {
            customItem = customItemRepository.findById(customItemDTO.getId())
                    .orElseGet(() -> customItemRepository.save(CustomItem.fromDTO(customItemDTO, gameSession)));
        } else {
            customItem = customItemRepository.save(CustomItem.fromDTO(customItemDTO, gameSession));
        }

        // Buscar el personaje (evitar crear instancias no administradas por JPA)
        CharacterEntity characterEntity = characterRepository.findById(characterId)
                .orElseThrow(() -> new EntityNotFoundException("Character not found"));

        // Buscar si ya existe una relación entre el personaje y el CustomItem
        CharacterItem characterItem = characterItemRepository
                .findByCharacterIdAndCustomItemId(characterId, customItem.getId())
                .orElseGet(() -> new CharacterItem(
                        new CharacterItemId(characterId, customItem.getId()),
                        quantity,
                        characterEntity,
                        customItem
                ));

        return CharacterItemResponseDTO.toDTO(characterItemRepository.save(characterItem));
    }

}
