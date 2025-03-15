package com.unir.character.service;

import com.unir.character.dto.CharacterItemDTO;
import com.unir.character.model.*;
import com.unir.character.repository.CharacterItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterItemService {

    // Inyección del repositorio
    @Autowired private  CharacterItemRepository characterItemRepository;


    // Método para obtener todos los items de un personaje
    public List<CharacterItemDTO> getCustomItemsByCharacter(Long characterId) {
        List<CharacterItem> items = characterItemRepository.findByCharacterId(characterId);
        return items.stream()
                .map(CharacterItemDTO::toDTO)
                .collect(Collectors.toList());
    }



    @Transactional
    public List<CharacterItemDTO> upsertItemsToCharacter(
            List<CharacterItemDTO> incomingItems
    ) {
        List<CharacterItemDTO> result = new ArrayList<>();
        Long characterId = incomingItems.get(0).getCharacterId();

        for (CharacterItemDTO incomingItem : incomingItems) {

            // 1. Buscar el ítem existente en la base de datos
            Long itemId = incomingItem.getCustomItem().getId();
            Optional<CharacterItem> existingItemOpt = characterItemRepository
                    .findByCharacterIdAndCustomItemId(characterId, itemId);

            // 2. Comparar los valores de updatedAt (Long)

            if (existingItemOpt.isPresent()) {
                CharacterItem existingItem = existingItemOpt.get();

                if (incomingItem.getUpdatedAt() > existingItem.getUpdatedAt()) {
                    if (incomingItem.getQuantity() <= 0) {
                        characterItemRepository.deleteByCharacterIdAndItemId(characterId, itemId);
                        System.out.println("NÚMERO NEGATIVOS DE OBJETOSSS, BORRAAAAAAANDOOOOOO");

                    } else {
                        System.out.println("NÚMERO POSITIVO INSERTANDO");

                        characterItemRepository.upsertCharacterItem(
                                characterId,
                                itemId,
                                incomingItem.getQuantity(),
                                incomingItem.getUpdatedAt()
                        );
                    }
                    result.add(incomingItem);
                } else {
                    result.add(CharacterItemDTO.toDTO(existingItem));
                }
            } else {
                // El ítem no existe en la base de datos: insertarlo
                if (incomingItem.getQuantity() >= 0) {
                    characterItemRepository.upsertCharacterItem(
                            characterId,
                            itemId,
                            incomingItem.getQuantity(),
                            incomingItem.getUpdatedAt()
                    );
                    result.add(incomingItem);
                } else {
                    System.out.println("NÚMERO NEGATIVO DETECTADO, NO INSERTANDO");
                }

            }
        }

        return result;
    }


    // Método para eliminar un item de un personaje
    public void  deleteItemFromCharacter(Long characterId, Long itemId) {
        characterItemRepository.deleteByCharacterIdAndItemId(characterId, itemId);

    }



}
