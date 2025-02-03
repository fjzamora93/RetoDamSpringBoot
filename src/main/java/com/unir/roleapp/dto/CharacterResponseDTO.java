package com.unir.roleapp.dto;
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
public class CharacterResponseDTO {
    private Long id;
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

    // En lugar de solo IDs, podrías devolver objetos con más detalles si es necesario (FUERTE ACOPLAMIENTO, ESTÁS LLEVANDO LA ENTITY ENTERA Y NO UN DTO)
    private Long userId;   // TOMAMOS SOLAMENTE LA ID, NO EL USER COMPLETO
    private RoleClassDTO roleClass;
    private List<ItemDTO> items;
    private List<SkillDTO> skills;
}
