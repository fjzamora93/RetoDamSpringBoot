package com.unir.roleapp.service;

import com.unir.roleapp.dto.SpellDTO;
import com.unir.roleapp.entity.Spell;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import com.unir.roleapp.repository.RoleClassRepository;
import com.unir.roleapp.repository.SpellRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpellService {

    @Autowired private ModelMapper modelMapper;

    @Autowired private SpellRepository spellRepository;
    @Autowired private RoleClassRepository roleClassRepository;
    @Autowired private EntityToDtoMapper entityToDtoMapper;


    public List <SpellDTO> getAllSpells(){
        List<Spell> spells = spellRepository.findAll();
        return spells
                .stream()
                .map(spell -> modelMapper.map(spell, SpellDTO.class))
                .collect(Collectors.toList());
    }

    public List <SpellDTO>  getSpellsByLevelAndRoleClass(
            int level,
            String roleClass
    ){
        List<Spell> spells = spellRepository.findSpellsByLevelAndRoleClass(level, roleClass);
        return spells
                .stream()
                .map(spell -> modelMapper.map(spell, SpellDTO.class))
                .collect(Collectors.toList());
    }

}
