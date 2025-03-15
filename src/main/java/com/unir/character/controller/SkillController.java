package com.unir.character.controller;

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

    /**Skill por character id*/
    @GetMapping("{characterId}")
    public ResponseEntity<List<SkillDTO>> getSkillsByCharacterId(@PathVariable Long characterId) {
        List<SkillDTO> skills = skillService.getSkillsByCharacterId(characterId);
        return ResponseEntity.ok(skills);
    }

    /**AÑadir set de skills por defecto*/
    @PostMapping("/addDefault/{characterId}")
    public ResponseEntity<List<SkillDTO>> addDefaultSkills(
            @PathVariable Long characterId,
            @RequestBody List<Long> skillIds
    ) {
        List<SkillDTO> addedSkills = skillService.addDefaultSkills(characterId, skillIds);
        if (addedSkills.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(addedSkills);
    }

    /** Añadir Skill a Character */
    @PostMapping()
    public ResponseEntity<CharacterEntity> addSkillToCharacter(
            @RequestParam Long characterId,
            @RequestParam Long skillId) {
        CharacterEntity updatedCharacter = skillService.addSkillToCharacterEntity(characterId, skillId);
        return ResponseEntity.ok(updatedCharacter);
    }

    /** Eliminar Skill de Character */
    @DeleteMapping()
    public ResponseEntity<CharacterEntity> deleteSkillFromCharacter(
            @RequestParam Long characterId,
            @RequestParam Long skillId) {
        CharacterEntity updatedCharacter = skillService.deleteSkillFromCharacterEntity(characterId, skillId);
        return ResponseEntity.ok(updatedCharacter);
    }

}
