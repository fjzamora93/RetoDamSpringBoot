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
    private Long userId;
}
