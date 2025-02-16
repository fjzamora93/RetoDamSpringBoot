package com.unir.roleapp.dto;

import com.unir.roleapp.entity.CharacterEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameSessionDTO {
    private Long id;
    private Long userId; // Solo el ID del usuario para evitar traer todo el objeto User
    private List<CharacterEntity> characters;
    private List<CustomItemDTO> customItems;

    public GameSessionDTO(Long userId, List<CharacterEntity> characters, List<CustomItemDTO> customItems) {
        this.userId = userId;
        this.characters = characters;
        this.customItems = customItems;
    }
}
