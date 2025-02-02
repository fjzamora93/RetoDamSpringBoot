package com.unir.roleapp.service;

import com.unir.roleapp.dto.CharacterRequestDTO;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {
    @Autowired
    private CharacterRepository characterRepository;

//    @Autowired
//    private RoleClassRepository roleClassRepository;
//
//    @Autowired
//    private SpellRepository spellRepository;
//
//    @Autowired
//    private SkillRepository skillRepository;
//
//    @Autowired
//    private ItemRepository itemRepository;

    public CharacterEntity saveOrUpdateCharacter(CharacterRequestDTO dto) {
        CharacterEntity character;

        if (dto.getId() == null) {
            // 🚀 CREACIÓN
            character = new CharacterEntity();
        } else {
            // 🔄 ACTUALIZACIÓN
            character = characterRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Character not found"));
        }

//        character.setName(dto.getName());
//        character.setDescription(dto.getDescription());
//        character.setRace(dto.getRace());
//        character.setGender(dto.getGender());
//        character.setSize(dto.getSize());
//        character.setAge(dto.getAge());
//        character.setGold(dto.getGold());
//        character.setStrength(dto.getStrength());
//        character.setDexterity(dto.getDexterity());
//        character.setConstitution(dto.getConstitution());
//        character.setIntelligence(dto.getIntelligence());
//        character.setWisdom(dto.getWisdom());
//        character.setCharisma(dto.getCharisma());
//        character.setImgUrl(dto.getImgUrl());
//
//        // Buscar el RoleClass por ID
//        if (dto.getRoleClassId() != null) {
//            RoleClass roleClass = roleClassRepository.findById(dto.getRoleClassId())
//                    .orElseThrow(() -> new RuntimeException("Role class not found"));
//            character.setRoleClass(roleClass);
//        }
//
//        // Buscar los hechizos por ID
//        if (dto.getSpellIds() != null) {
//            List<Spell> spells = spellRepository.findAllById(dto.getSpellIds());
//            character.setSpells(spells);
//        }
//
//        // Buscar las habilidades por ID
//        if (dto.getSkillIds() != null) {
//            List<Skill> skills = skillRepository.findAllById(dto.getSkillIds());
//            character.setSkills(skills);
//        }
//
//        // Buscar los ítems por ID
//        if (dto.getItemIds() != null) {
//            List<Item> items = itemRepository.findAllById(dto.getItemIds());
//            character.setItems(items);
//        }

        return characterRepository.save(character);
    }
}
