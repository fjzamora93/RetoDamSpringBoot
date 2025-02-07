package com.unir.roleapp.controller;

import com.unir.roleapp.dto.SpellDTO;
import com.unir.roleapp.entity.Spell;
import com.unir.roleapp.repository.SpellRepository;
import com.unir.roleapp.service.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/spells")
public class SpellController {

    @Autowired
    private SpellService spellService;

    /** MOSTRAR TODOS */
    @GetMapping
    public List<SpellDTO> getAllSpells() {
        return spellService.getAllSpells();
    }

    /** FILTRAR EN FUNCIÓN DEL NIVEL Y CLASE DEL PERSONAJE */
    @GetMapping("/costum-query")
    public List<SpellDTO> getSpellsByLevelAndRoleClass(
            @RequestParam int level,
            @RequestParam String roleClass
    ) {
        return spellService.getSpellsByLevelAndRoleClass(level, roleClass);
    }


}
