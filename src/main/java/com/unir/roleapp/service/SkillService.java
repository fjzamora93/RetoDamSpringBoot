package com.unir.roleapp.service;

import com.unir.roleapp.dto.SkillDTO;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.entity.Skill;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import com.unir.roleapp.repository.CharacterRepository;
import com.unir.roleapp.repository.SkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {
    @Autowired private ModelMapper modelMapper;
    @Autowired private EntityToDtoMapper entityToDtoMapper;

    @Autowired private SkillRepository skillRepository;
    @Autowired private CharacterRepository characterRepository;

    public List<SkillDTO> getSkills() {
        List <Skill> skills = skillRepository.findAll();
        return skills.stream().map(skill -> modelMapper.map(skill, SkillDTO.class)).collect(Collectors.toList());
    }

    /** Añadir SKill a Character */
    public CharacterEntity addSkillToCharacterEntity(
            Long charcterId,
            Long skillId
    ) {
        CharacterEntity character = characterRepository.findById(charcterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));

        character.getSkills().add(skill);
        return characterRepository.save(character);
    }

    /** Eliminar Skill de Character */
    public CharacterEntity deleteSkillFromCharacterEntity(Long characterId, Long skillId) {
        CharacterEntity character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));

        // Eliminar la skill del personaje
        character.getSkills().remove(skill);

        // Guardar el personaje con la habilidad eliminada
        return characterRepository.save(character);
    }


}
