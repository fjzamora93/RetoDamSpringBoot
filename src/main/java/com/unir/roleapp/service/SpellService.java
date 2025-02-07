package com.unir.roleapp.service;

import com.unir.roleapp.dto.SpellDTO;
import com.unir.roleapp.entity.RoleClass;
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
    @Autowired private EntityToDtoMapper entityToDtoMapper;

    @Autowired private SpellRepository spellRepository;
    @Autowired private RoleClassRepository roleClassRepository;

    /**
     * Obtiene una lista de hechizos (DTO) donde cada hechizo contiene a su vez una lista con los NAME
     * de las clases que pueden usar dichos hechizos.
     *
     * El .map(spell ->
     * 1. Aprovecha la relación @ManyToMany de Spell para extraer el nombre de las clases asociadas.
     * 2. Devuelve la lista de SPell como DTO.
     *
     * */
    public List <SpellDTO> getAllSpells(){
        List<Spell> spells = spellRepository.findAll();
        return spells.stream()
                .map(spell -> {
                    List<String> roleClassNames = spell.getRoleClasses()
                            .stream()
                            .map(RoleClass::getName)
                            .collect(Collectors.toList());

                    return entityToDtoMapper.mapSpellWithClassToDTO(spell, roleClassNames);
                })
                .collect(Collectors.toList());
    }

    public List<SpellDTO> getSpellsByLevelAndRoleClass(int level, String roleClass) {
        List<Spell> spells = spellRepository.findSpellsByLevelAndRoleClass(level, roleClass);

        return spells.stream()
                .map(spell -> {
                    List<String> roleClassNames = spell.getRoleClasses()
                            .stream()
                            .map(RoleClass::getName)
                            .collect(Collectors.toList());

                    return entityToDtoMapper.mapSpellWithClassToDTO(spell, roleClassNames);
                })
                .collect(Collectors.toList());
    }


}
