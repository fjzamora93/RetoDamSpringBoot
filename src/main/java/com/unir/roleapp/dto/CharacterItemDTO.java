package com.unir.roleapp.dto;

import com.unir.roleapp.model.CharacterItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterItemDTO {
    private Long characterId;
    private int quantity;

    private CustomItemDTO customItem;


    public static CharacterItemDTO toDTO(CharacterItem characterItem) {

        if (characterItem == null) {
            return null;
        }
        return new CharacterItemDTO(
                characterItem.getCharacter().getId(),
                characterItem.getQuantity(),
                characterItem.getCustomItem().toDTO()
        );
    }

}
