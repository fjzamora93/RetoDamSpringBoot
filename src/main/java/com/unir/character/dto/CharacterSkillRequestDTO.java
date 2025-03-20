package com.unir.character.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterSkillRequestDTO {
    private Long characterId;
    private Long skillId;
    private int value;
}
