package com.unir.roleapp.controller;

import com.unir.roleapp.dto.SpellDTO;
import com.unir.roleapp.service.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/spells")
public class SpellController {

    @Autowired
    private SpellService spellService;

    /** MOSTRAR TODOS */
    @GetMapping
    public ResponseEntity<List<SpellDTO>> getAllSpells() {
        List<SpellDTO> spells = spellService.getAllSpells();
        return  ResponseEntity.ok(spells);
    }

    /** FILTRAR EN FUNCIÓN DEL NIVEL Y CLASE DEL PERSONAJE */
    @GetMapping("/filter")
    public ResponseEntity<List<SpellDTO>> getSpellsByLevelAndRoleClass(
            @RequestParam int level,
            @RequestParam String roleClass
    ) {
        List<SpellDTO> spells = spellService.getSpellsByLevelAndRoleClass(level, roleClass);
        return  ResponseEntity.ok(spells);
    }


}
