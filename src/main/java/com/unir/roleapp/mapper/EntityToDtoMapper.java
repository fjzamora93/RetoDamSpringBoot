package com.unir.roleapp.mapper;

import com.unir.roleapp.dto.*;
import com.unir.roleapp.entity.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


/**
 * Mapea de CHaracterEntity a CharacterResponseDTO.
 * Para mapear correctamente, el mapeo se realiza en las siguietnes fases:
 * - Mapea el propio personaje.
 * - Mapea sus Item.
 * - Mapea sus Skills.
 * - Mapea el RoleCLass del personaje.
 * - Mapea los Spells que están dentro de la lista de RoleCLass
 * */
@Component
public class EntityToDtoMapper {

    public CharacterResponseDTO mapToCharacterResponseDTO(CharacterEntity characterEntity) {
        CharacterResponseDTO dto = new CharacterResponseDTO();
        dto.setId(characterEntity.getId());
        dto.setName(characterEntity.getName());
        dto.setDescription(characterEntity.getDescription());
        dto.setRace(characterEntity.getRace());
        dto.setGender(characterEntity.getGender());
        dto.setSize(characterEntity.getSize());
        dto.setAge(characterEntity.getAge());
        dto.setGold(characterEntity.getGold());
        dto.setStrength(characterEntity.getStrength());
        dto.setDexterity(characterEntity.getDexterity());
        dto.setConstitution(characterEntity.getConstitution());
        dto.setIntelligence(characterEntity.getIntelligence());
        dto.setWisdom(characterEntity.getWisdom());
        dto.setCharisma(characterEntity.getCharisma());
        dto.setImgUrl(characterEntity.getImgUrl());
        dto.setUserId(characterEntity.getUser().getId());
        dto.setRoleClass(mapRoleClassToDTO(characterEntity.getRoleClass()));
        dto.setItems(characterEntity.getItems().stream()
                .map(this::mapItemToDTO)
                .collect(Collectors.toList()));
        dto.setSkills(characterEntity.getSkills().stream()
                .map(this::mapSkillToDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private RoleClassDTO mapRoleClassToDTO(RoleClass roleClass) {
        RoleClassDTO dto = new RoleClassDTO();
        dto.setId(roleClass.getId());
        dto.setName(roleClass.getName());
        dto.setSpells(roleClass.getSpells().stream()
                .map(this::mapSpellToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private ItemDTO mapItemToDTO(Item item) {
        return new ItemDTO(item.getId(), item.getName(), item.getDescription(),
                item.getImgUrl(), item.getGoldValue(), item.getCategory(), item.getDice());
    }

    private SkillDTO mapSkillToDTO(Skill skill) {
        return new SkillDTO(skill.getId(), skill.getName(), skill.getDescription());
    }

    private SpellDTO mapSpellToDTO(Spell spell) {
        return new SpellDTO(spell.getId(), spell.getName(), spell.getDescription(),
                spell.getDice(), spell.getLevel(), spell.getCost(), spell.getImgUrl());
    }
}
