package com.unir.character.dto;

import com.unir.character.model.CharacterItem;
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
    private Long updatedAt;

    public static CharacterItemDTO toDTO(CharacterItem characterItem) {

        if (characterItem == null) {
            return null;
        }
        return new CharacterItemDTO(
                characterItem.getCharacter().getId(),
                characterItem.getQuantity(),
                characterItem.getCustomItem().toDTO(),
                characterItem.getUpdatedAt()
        );
    }

    @Override
    public String toString() {
        return "CharacterItemDTO{" +
                "characterId=" + characterId +
                ", quantity=" + quantity +
                ", customItem=" + customItem +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
