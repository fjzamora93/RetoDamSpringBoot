package com.unir.roleapp.service;

import com.unir.roleapp.dto.*;
import com.unir.roleapp.entity.*;
import com.unir.roleapp.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterService {
    @Autowired private CharacterRepository characterRepository;

//    @Autowired private RoleClassRepository roleClassRepository;
//    @Autowired private SpellRepository spellRepository;
//    @Autowired private SkillRepository skillRepository;
//    @Autowired private ItemRepository itemRepository;

    // Obtener todos los personajes
    public List<CharacterResponseDTO> getAllCharacters() {
        List<CharacterEntity> characters = characterRepository.findAll();
        return characters.stream()
                .map(this::mapToCharacterResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener un personaje por su ID
    public Optional<CharacterResponseDTO> getCharacterById(Long id) {
        Optional<CharacterEntity> characterEntity = characterRepository.findById(id);
        return characterEntity.map(this::mapToCharacterResponseDTO);
    }


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

    // Método de mapeo de CharacterEntity a CharacterResponseDTO
    private CharacterResponseDTO mapToCharacterResponseDTO(CharacterEntity characterEntity) {
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
        // Mapeamos RoleClass, Items y Skills (si es necesario)

        dto.setRoleClass(mapRoleClassToDTO(characterEntity.getRoleClass()));
        dto.setItems(characterEntity.getItems().stream()
                .map(this::mapItemToDTO)
                .collect(Collectors.toList()));
        dto.setSkills(characterEntity.getSkills().stream()
                .map(this::mapSkillToDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    // Métodos de mapeo adicionales para cada entidad asociada

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
