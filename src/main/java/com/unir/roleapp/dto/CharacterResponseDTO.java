package com.unir.roleapp.dto;
import com.unir.roleapp.entity.*;
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
public class CharacterResponseDTO {
    private Long id;
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
    private Long userId;

    // En lugar de solo IDs, podrías devolver objetos con más detalles si es necesario
    private RoleClass roleClass;
    private List<Spell> spells;
    private List<Item> items;
    private List<Skill> skills;

    public CharacterResponseDTO(CharacterEntity character) {
        this.id = character.getId();
        this.name = character.getName();
        this.description = character.getDescription();
        this.race = character.getRace();
        this.gender = character.getGender();
        this.size = character.getSize();
        this.age = character.getAge();
        this.gold = character.getGold();
        this.strength = character.getStrength();
        this.dexterity = character.getDexterity();
        this.constitution = character.getConstitution();
        this.intelligence = character.getIntelligence();
        this.wisdom = character.getWisdom();
        this.charisma = character.getCharisma();
        this.imgUrl = character.getImgUrl();
        this.userId = character.getUser().getId();
        this.roleClass = character.getRoleClass();
        this.spells = character.getSpells();
        this.items = character.getItems();
        this.skills = character.getSkills();
    }
}
