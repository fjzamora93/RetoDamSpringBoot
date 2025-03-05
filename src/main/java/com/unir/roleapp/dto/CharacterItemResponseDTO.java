package com.unir.roleapp.dto;

import com.unir.roleapp.model.CharacterItem;
import com.unir.roleapp.model.CustomItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterItemResponseDTO {
    private Long characterId;
    private int quantity;

    private CustomItemDTO customItem;


    public static CharacterItemResponseDTO toDTO(CharacterItem characterItem) {

        if (characterItem == null) {
            return null;
        }
        return new CharacterItemResponseDTO(
                characterItem.getCharacter().getId(),
                characterItem.getQuantity(),
                characterItem.getCustomItem().toDTO()
        );
    }

}
