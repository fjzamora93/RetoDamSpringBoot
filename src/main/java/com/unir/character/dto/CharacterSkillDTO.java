package com.unir.character.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterSkillDTO {
    private Long idSkill;
    private String name;
    private String description;
    private int value;

}