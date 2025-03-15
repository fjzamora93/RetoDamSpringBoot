package com.unir.adventure.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameSessionDTO {
    private Long id;
    private Long userId;
}
