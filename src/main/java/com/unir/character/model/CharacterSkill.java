package com.unir.character.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "character_skill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterSkill {

    @EmbeddedId
    private CharacterSkillId id;

    @ManyToOne
    @MapsId("idCharacter") // Mapea la parte de idCharacter del EmbeddedId
    @JoinColumn(name = "id_character", nullable = false)
    private CharacterEntity character;

    @ManyToOne
    @MapsId("idSkill") // Mapea la parte de idSkill del EmbeddedId
    @JoinColumn(name = "id_skill", nullable = false)
    private Skill skill;

    @Column(name = "value", nullable = false)
    private int value;
}