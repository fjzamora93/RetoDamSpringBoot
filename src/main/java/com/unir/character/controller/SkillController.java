package com.unir.character.controller;

import com.unir.character.dto.CharacterSkillRequestDTO;
import com.unir.character.dto.SkillDTO;
import com.unir.character.model.CharacterEntity;
import com.unir.character.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAllSkills() {
        List<SkillDTO> skills = skillService.getSkills();
        return ResponseEntity.ok(skills);
    }


}
