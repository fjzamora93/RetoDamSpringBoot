package com.unir.character.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unir.character.dto.SkillDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "skill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name") private String name;
    @Column(name = "description") private String description;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CharacterSkill> characterSkills;


    public SkillDTO toSkillDTO() {
        return new SkillDTO(this.id, this.name, this.description);
    }
}
