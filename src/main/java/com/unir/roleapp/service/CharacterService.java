package com.unir.roleapp.service;

import com.unir.roleapp.dto.*;
import com.unir.roleapp.model.*;
import com.unir.roleapp.repository.*;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired private UserRepository userRepository;
    @Autowired private GameSessionRepository gameSessionRepository;


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
        List<CharacterEntity> characters = characterRepository.findByUser_Id(userId);
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
    public void deleteById(Long id) {
        if (!characterRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personaje no encontrado");
        }
        try {
            characterRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar el personaje porque está en uso.");
        }
    }


    /** CREA UN NUEVO PERSONAJE O SI EN EL REQUEST SE INCLUYE EL ID ACTUALIZA UNO YA EXISTENTE */
    @Transactional
    public CharacterResponseDTO saveOrUpdateCharacter(CharacterRequestDTO characterDto) {
        // Recuperar la entidad dentro de la transacción
        CharacterEntity characterEntity = characterRepository.findByIdWithLock(characterDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PERSONAJE NO ENCONTRADO"));

        modelMapper.map(characterDto, characterEntity);

        // 1. RoleClass
        RoleClass roleClass = roleClassRepository.findByName(characterDto.getRoleClass())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ROLCLASS NO ENCONTRADO"));
        characterEntity.setRoleClass(roleClass);

        // 2. GameSession
        GameSession gameSession = gameSessionRepository.findById(characterDto.getGameSessionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GAMESESSION NO ENCONTRADO"));
        characterEntity.setGameSession(gameSession);

        // 3. User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USUARIO NO ENCONTRADO"));
        characterEntity.setUser(user);
        CharacterEntity savedCharacter = characterRepository.save(characterEntity);

        System.out.println("Personaje guardado/actualizado: " + savedCharacter);

        return entityToDtoMapper.mapToCharacterResponseDTO(savedCharacter);
    }



}
