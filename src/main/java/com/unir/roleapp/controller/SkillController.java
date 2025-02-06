package com.unir.roleapp.controller;

import com.unir.roleapp.dto.SkillDTO;
import com.unir.roleapp.entity.Skill;
import com.unir.roleapp.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping
    public List<SkillDTO> getAllSkills() {
        return new ArrayList<SkillDTO>();
    }
}
