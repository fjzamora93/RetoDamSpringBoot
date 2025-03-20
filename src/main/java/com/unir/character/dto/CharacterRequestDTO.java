package com.unir.character.dto;
import com.unir.character.enumm.Gender;
import com.unir.character.enumm.Race;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterRequestDTO {
    private Long id; // VERIFICA QUE EL ID ES NULL AL CREAR UN PERSONAJE. SI ES NULO, SE CREA, SI NO ES NULO, SE TIENE QUE HACER UN UPDATE.
    private Long updatedAt;


    private String name;
    private String description;
    private Race race;
    private Gender gender;
    private int level;
    private int armor;
    private int age;
    private int gold;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private String imgUrl;

    // RELACIONES
    private Long userId;
    private String roleClass;
    private Long gameSessionId;
    private List<CharacterSkillRequestDTO> characterSkills;

    public CharacterRequestDTO(
           Long updatedAt,
            String name, String description, Race race, Gender gender, int armor, int level, int age, int gold,
            int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma,
            String imgUrl, Long userId, String roleClass, List<CharacterSkillRequestDTO> characterSkills)
    {
        this.updatedAt = updatedAt;
        this.name = name;
        this.description = description;
        this.race = race;
        this.gender = gender;
        this.level = level;
        this.armor = armor;
        this.age = age;
        this.gold = gold;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.imgUrl = imgUrl;
        this.userId = userId;

        this.roleClass = roleClass;
        this.characterSkills = characterSkills;
    }
}
