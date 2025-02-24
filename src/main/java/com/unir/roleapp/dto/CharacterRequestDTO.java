package com.unir.roleapp.dto;
import com.unir.roleapp.entity.GameSession;
import com.unir.roleapp.enumm.Gender;
import com.unir.roleapp.enumm.Race;
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
    private int size;
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
    private GameSession gameSession;

    public CharacterRequestDTO(
            String name, String description, Race race, Gender gender, int size, int age, int gold,
            int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma,
            String imgUrl, Long userId, String roleClass)
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

        this.roleClass = roleClass;
    }
}
