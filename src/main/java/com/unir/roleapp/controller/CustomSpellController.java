package com.unir.roleapp.controller;

import com.unir.roleapp.dto.CustomSpellDTO;
import com.unir.roleapp.entity.CustomSpell;
import com.unir.roleapp.service.CustomSpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/custom_spells")
public class CustomSpellController {


    @Autowired private CustomSpellService customSpellService;

    // CUSTOM SPELLS BY CHARACTER ID
    @GetMapping("/character/{id}")
    public ResponseEntity<List<CustomSpellDTO>> findCustomSpellByCharacterId(
            @PathVariable("id") Long characterId) {
        List<CustomSpellDTO> spells =customSpellService.getSpellsByCharacterId(characterId);
        return ResponseEntity.ok(spells);
    }


    // ADD CUSTOM SPELL TO CHARACTER
    @PostMapping
    public ResponseEntity<CustomSpellDTO> createCustomSpell(
            @RequestBody CustomSpellDTO customSpellDTO
    ){
        CustomSpellDTO savedSpell = customSpellService.saveOrUpdateSpell(customSpellDTO);
        return ResponseEntity.ok(savedSpell);
    }


    // DELETE CUSTOM SPELL FROM CHARACTER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomSpell(
            @PathVariable Long id
    ){
        customSpellService.deleteSpell(id);
        return ResponseEntity.noContent().build();
    }

}
