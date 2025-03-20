package com.unir.character.mapper;

import com.unir.character.dto.*;
import com.unir.character.model.*;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class EntityToDtoMapper {
    @Autowired private ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        // Configurar mapeo de CharacterEntity a CharacterResponseDTO
        modelMapper.createTypeMap(CharacterEntity.class, CharacterResponseDTO.class)
                .addMappings(mapper -> {
                    // Mapear gameSession.id a gameSessionId
                    mapper.map(src -> src.getGameSession().getId(), CharacterResponseDTO::setGameSessionId);
                    // Mapear user.id a userId
                    mapper.map(src -> src.getUser().getId(), CharacterResponseDTO::setUserId);
                    // Ignorar campos que se asignarán manualmente
                    mapper.skip(CharacterResponseDTO::setRoleClass);
                    mapper.skip(CharacterResponseDTO::setItems);
                    mapper.skip(CharacterResponseDTO::setSkills);
                });

        // Configurar mapeo de CharacterRequestDTO a CharacterEntity (existente)
        modelMapper.createTypeMap(CharacterRequestDTO.class, CharacterEntity.class)
                .addMappings(mapper -> {
                    mapper.skip(CharacterEntity::setGameSession);
                    mapper.skip(CharacterEntity::setRoleClass);
                    mapper.skip(CharacterEntity::setUser);
                    mapper.skip(CharacterEntity::setCharacterSkills);
                });
    }

    public CharacterResponseDTO mapToCharacterResponseDTO(CharacterEntity characterEntity) {
        CharacterResponseDTO dto = modelMapper.map(characterEntity, CharacterResponseDTO.class);

        // Asignaciones manuales SOLO para campos complejos
        dto.setRoleClass(mapRoleClassToDTO(characterEntity.getRoleClass()));

        // Mapear characterSkills a CharacterSkillDTO
        dto.setSkills(characterEntity.getCharacterSkills().stream()
                .map(this::mapCharacterSkillToDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private CharacterSkillDTO mapCharacterSkillToDTO(CharacterSkill characterSkill) {
        CharacterSkillDTO dto = new CharacterSkillDTO();
        dto.setSkill(characterSkill.getSkill().toSkillDTO());
        dto.setValue(characterSkill.getValue());
        return dto;
    }

    private RoleClassDTO mapRoleClassToDTO(RoleClass roleClass) {
        RoleClassDTO dto = new RoleClassDTO();
        dto.setId(roleClass.getId());
        dto.setName(roleClass.getName());
        return dto;
    }


    public SpellDTO mapSpellWithClassToDTO(Spell spell, List<String> className) {
        return new SpellDTO(spell.getId(), spell.getName(), spell.getDescription(),
                spell.getDice(), spell.getDicesAmount(), spell.getLevel(), spell.getCost(), spell.getImgUrl(),
                className
        );
    }

    public CustomSpellDTO mapCustomSpellToDTO(CustomSpell spell) {
        return new CustomSpellDTO(spell.getId(), spell.getName(), spell.getDescription(),
                spell.getDice(), spell.getDicesAmount(), spell.getLevel(), spell.getCost(), spell.getImgUrl(),
                spell.getCharacter().getId()
        );
    }
}
