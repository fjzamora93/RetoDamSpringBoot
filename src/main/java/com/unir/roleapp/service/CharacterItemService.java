package com.unir.roleapp.service;

import com.unir.roleapp.dto.CharacterItemResponseDTO;
import com.unir.roleapp.model.CharacterEntity;
import com.unir.roleapp.model.CharacterItem;
import com.unir.roleapp.model.CustomItem;
import com.unir.roleapp.repository.CharacterItemRepository;
import com.unir.roleapp.repository.CharacterRepository;
import com.unir.roleapp.repository.CustomItemRepository;
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
    private final CharacterItemRepository characterItemRepository;

    // Método para agregar o actualizar un item a un personaje
    public CharacterItemResponseDTO addOrUpdateItemToCharacter(Long characterId, Long itemId, int quantity) {
        // Buscar si ya existe una relación entre el personaje y el item
        Optional<CharacterItem> existingItemOpt = characterItemRepository.findByCharacterId(characterId)
                .stream()
                .filter(ci -> ci.getCustomItem().getId().equals(itemId))
                .findFirst();

        CharacterItem characterItem;

        if (existingItemOpt.isPresent()) {
            // Si ya existe, actualizar la cantidad
            characterItem = existingItemOpt.get();
            characterItem.setQuantity(quantity);
        } else {
            // Si no existe, crear un nuevo CharacterItem
            characterItem = new CharacterItem();
            characterItem.setQuantity(quantity);

            // Establecer las relaciones de personaje e item (asumiendo que tienes métodos de acceso a CharacterEntity y CustomItem)
            CharacterEntity characterEntity = new CharacterEntity();
            characterEntity.setId(characterId);

            CustomItem customItem = new CustomItem();
            customItem.setId(itemId);

            characterItem.setCharacter(characterEntity);
            characterItem.setCustomItem(customItem);
        }

        // Guardar el CharacterItem (agregar o actualizar)
        CharacterItem savedItem = characterItemRepository.save(characterItem);

        // Retornar el DTO usando el mapeo
        return CharacterItemResponseDTO.toDTO(savedItem);
    }

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
}
