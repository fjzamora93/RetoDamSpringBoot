package com.unir.character.service;

import com.unir.character.dto.CharacterSkillRequestDTO;
import com.unir.character.dto.SkillDTO;
import com.unir.character.model.CharacterEntity;
import com.unir.character.model.CharacterSkill;
import com.unir.character.model.Skill;
import com.unir.character.repository.CharacterRepository;
import com.unir.character.repository.SkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {
    @Autowired private ModelMapper modelMapper;

    @Autowired private SkillRepository skillRepository;
    @Autowired private CharacterRepository characterRepository;

    public List<SkillDTO> getSkills() {
        List <Skill> skills = skillRepository.findAll();
        return skills.stream().map(
                skill -> modelMapper.map(skill, SkillDTO.class))
                .collect(Collectors.toList());
    }


}






