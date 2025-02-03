package com.unir.roleapp.dto;
import com.unir.roleapp.enumm.Gender;
import com.unir.roleapp.enumm.Race;
import com.unir.roleapp.enumm.Range;
import jakarta.persistence.*;
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
    private String name;
    private String description;
    private Race race;
    private Gender gender;
    private Range size;
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
    private List<Long> itemIds;
    private List<Long> skillIds;
    private Long roleClassId;

    public CharacterRequestDTO(
            String name, String description, Race race, Gender gender, Range size, int age, int gold,
            int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma,
            String imgUrl, Long userId, List<Long> itemIds,  List<Long> skillIds, Long roleClassId)
    {
        this.name = name;
        this.description = description;
        this.race = race;
        this.gender = gender;
        this.size = size;
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
        this.itemIds = itemIds;
        this.skillIds = skillIds;
        this.roleClassId = roleClassId;
    }
}
