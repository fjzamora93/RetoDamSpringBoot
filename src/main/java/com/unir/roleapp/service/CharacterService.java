package com.unir.roleapp.service;

import com.unir.roleapp.dto.*;
import com.unir.roleapp.entity.*;
import com.unir.roleapp.repository.*;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterService {
    /** ModelMapper toma las mismos campos comunes entre CharacterRequestDTO y CharacterENtity y los mapea.
     * El resto de relaciones deben ser manejadas manualmente, ya que son relaciones complejas.
     * */
    @Autowired
    private ModelMapper modelMapper;

    @Autowired private CharacterRepository characterRepository;
    @Autowired private EntityToDtoMapper entityToDtoMapper;
    @Autowired private RoleClassRepository roleClassRepository;
    @Autowired private SkillRepository skillRepository;
    @Autowired private ItemRepository itemRepository;


    // Obtener todos los personajes
    public List<CharacterResponseDTO> getAllCharacters() {
        List<CharacterEntity> characters = characterRepository.findAll();
        return characters.stream()
                .map(entityToDtoMapper::mapToCharacterResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener un personaje por su ID
    public Optional<CharacterResponseDTO> getCharacterById(Long id) {
        Optional<CharacterEntity> characterEntity = characterRepository.findById(id);
        return characterEntity.map(entityToDtoMapper::mapToCharacterResponseDTO);
    }


    public CharacterResponseDTO saveOrUpdateCharacter(CharacterRequestDTO characterDto) {
        RoleClass roleClass = roleClassRepository.findById(characterDto.getRoleClassId())
                .orElseThrow(() -> new RuntimeException("Role class not found"));

        CharacterEntity characterEntity = modelMapper.map(characterDto, CharacterEntity.class);
        characterEntity.setRoleClass(roleClass);


        // Buscar las habilidades por ID
        if (characterDto.getSkillIds() != null) {
            List<Skill> skills = skillRepository.findAllById(characterDto.getSkillIds());
            characterEntity.setSkills(skills);
        }

        // Buscar los ítems por ID
        if (characterDto.getItemIds() != null) {
            List<Item> items = itemRepository.findAllById(characterDto.getItemIds());
            characterEntity.setItems(items);
        }

        CharacterEntity savedCharacter = characterRepository.save(characterEntity);
        return entityToDtoMapper.mapToCharacterResponseDTO(savedCharacter);
    }

}
