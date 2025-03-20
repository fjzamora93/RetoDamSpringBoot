package com.unir.character.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class CharacterSkillId implements Serializable {

    @Column(name = "id_character", nullable = false)
    private Long idCharacter;

    @Column(name = "id_skill", nullable = false)
    private Long idSkill;
}