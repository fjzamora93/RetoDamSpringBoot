package com.unir.roleapp.service;

import com.unir.roleapp.dto.CustomSpellDTO;
import com.unir.roleapp.dto.SpellDTO;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.entity.CustomSpell;
import com.unir.roleapp.entity.RoleClass;
import com.unir.roleapp.entity.Spell;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import com.unir.roleapp.repository.CharacterRepository;
import com.unir.roleapp.repository.RoleClassRepository;
import com.unir.roleapp.repository.CustomSpellRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomSpellService {
    @Autowired private ModelMapper modelMapper;
    @Autowired private EntityToDtoMapper entityToDtoMapper;

    @Autowired private CustomSpellRepository spellRepository;
    @Autowired private CharacterRepository characterRepository;
    @Autowired
    private CustomSpellRepository customSpellRepository;

    public List<CustomSpellDTO> getAllSpells(){
        List<CustomSpell> spells = spellRepository.findAll();
        return spells
                .stream()
                .map(spell -> entityToDtoMapper.mapCustomSpellToDTO(spell))
                .toList();
    }

    public List<CustomSpellDTO> getSpellsByCharacterId(long characterId){
        List<CustomSpell> spells = spellRepository.findByCharacter_Id(characterId);
        return spells
                .stream()
                .map(spell -> entityToDtoMapper.mapCustomSpellToDTO(spell))
                .toList();
    }

    public CustomSpellDTO saveOrUpdateSpell(CustomSpellDTO customSpellDTO) {
        CustomSpell customSpell = modelMapper.map(customSpellDTO, CustomSpell.class);
        CharacterEntity character = characterRepository.findById(customSpellDTO.getCharacterId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CHARACTER NOT FOUND"));

        customSpell.setCharacter(character);
        CustomSpell savedSpell = spellRepository.save(customSpell);
        return entityToDtoMapper.mapCustomSpellToDTO(savedSpell);
    }

    public void deleteSpell(long customSpellId) {
        customSpellRepository.deleteById(customSpellId);
    }

}
