package com.unir.roleapp.mapper;

import com.unir.roleapp.dto.*;
import com.unir.roleapp.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
    @Autowired private ModelMapper modelMapper;

    public CharacterResponseDTO mapToCharacterResponseDTO(CharacterEntity characterEntity) {
        CharacterResponseDTO dto = modelMapper.map(characterEntity, CharacterResponseDTO.class);

        if (characterEntity.getGameSession() != null) {
            dto.setGameSessionId(characterEntity.getGameSession().getId());

        }
        dto.setUserId(characterEntity.getUser().getId());
        dto.setRoleClass(mapRoleClassToDTO(characterEntity.getRoleClass()));
        dto.setItems(characterEntity.getCustomItems().stream()
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

    private CustomItemDTO mapItemToDTO(CustomItem customItem) {
        return new CustomItemDTO(
                customItem.getId(), customItem.getName(), customItem.getDescription(),
                customItem.getImgUrl(), customItem.getGoldValue(), customItem.getCategory(), customItem.getDice(),
                customItem.getStatType(), customItem.getStatValue()
        );
    }

    private SkillDTO mapSkillToDTO(Skill skill) {
        return new SkillDTO(skill.getId(), skill.getName(), skill.getDescription());
    }

    public SpellDTO mapSpellToDTO(Spell spell) {
        return new SpellDTO(spell.getId(), spell.getName(), spell.getDescription(),
                spell.getDice(), spell.getLevel(), spell.getCost(), spell.getImgUrl());
    }


    public SpellDTO mapSpellWithClassToDTO(Spell spell, List<String> className) {
        return new SpellDTO(spell.getId(), spell.getName(), spell.getDescription(),
                spell.getDice(), spell.getLevel(), spell.getCost(), spell.getImgUrl(),
                className
        );
    }
}
