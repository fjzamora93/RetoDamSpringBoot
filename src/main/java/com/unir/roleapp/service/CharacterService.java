package com.unir.roleapp.service;

import com.unir.roleapp.dto.*;
import com.unir.roleapp.entity.*;
import com.unir.roleapp.repository.*;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterService {
    /** ModelMapper toma las mismos campos comunes entre CharacterRequestDTO y CharacterENtity y los mapea.
     * El resto de relaciones deben ser manejadas manualmente, ya que son relaciones complejas.
     * */
    @Autowired private ModelMapper modelMapper;

    @Autowired private CharacterRepository characterRepository;
    @Autowired private EntityToDtoMapper entityToDtoMapper;
    @Autowired private RoleClassRepository roleClassRepository;
    @Autowired private SkillRepository skillRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private UserRepository userRepository;


    /** LISTA COMPLETA DE PERSONAJES
     * - Stream: convierte una colección a un flujo de datos stream (para procesarlos de forma más eficiente).
     * - Map: transforma cada objeto del flujo... es como una arrow function pero de Java.
     * - Collect: después d ehacer el map, coge los elementos procesados y los devuelve como una lsita.
     * */
    public List<CharacterResponseDTO> getAllCharacters() {
        List<CharacterEntity> characters = characterRepository.findAll();
        return characters.stream()
                .map(characterEntity -> entityToDtoMapper.mapToCharacterResponseDTO(characterEntity))
                .collect(Collectors.toList());
    }


    /** LISTA DE PERSONAJES POR USUARIO */
    public List<CharacterResponseDTO> getCharactersByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        List<CharacterEntity> characters = characterRepository.findByUser(user);
        return characters.stream()
                .map(entityToDtoMapper::mapToCharacterResponseDTO)
                .collect(Collectors.toList());
    }


    /** BUSCAR POR ID */
    public CharacterResponseDTO getCharacterById(Long id) {
        return characterRepository.findById(id)
                .map(entityToDtoMapper::mapToCharacterResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CHARACTER NOT FOUND"));

    }

    /** DELETE */
    public void deleteCharacter(Long id) {
        if (!characterRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personaje no encontrado");
        }
        characterRepository.deleteById(id);
    }

    /** CREA UN NUEVO PERSONAJE O SI EN EL REQUEST SE INCLUYE EL ID ACTUALIZA UNO YA EXISTENTE */
    public CharacterResponseDTO saveOrUpdateCharacter(CharacterRequestDTO characterDto) {
        RoleClass roleClass = roleClassRepository.findById(characterDto.getRoleClassId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ROLCLASS NOT FOUND"));

        CharacterEntity characterEntity = modelMapper.map(characterDto, CharacterEntity.class);
        characterEntity.setRoleClass(roleClass);

        // Buscar las habilidades por ID
        if (characterDto.getSkillIds() != null) {
            List<Skill> skills = skillRepository.findAllById(characterDto.getSkillIds());
            characterEntity.setSkills(skills);
        }

        // Buscar los ítems por ID
        if (characterDto.getItemIds() != null) {
            List<CustomItem> customItems = itemRepository.findAllById(characterDto.getItemIds());
            characterEntity.setCustomItems(customItems);
        }

        CharacterEntity savedCharacter = characterRepository.save(characterEntity);
        return entityToDtoMapper.mapToCharacterResponseDTO(savedCharacter);
    }





}
