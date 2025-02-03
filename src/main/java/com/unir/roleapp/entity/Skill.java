package com.unir.roleapp.entity;

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
    private int id;

    @Column(name = "name") private String name;
    @Column(name = "description") private String description;

//    UNIDIRECCIONAL
//    @ManyToMany(
//            mappedBy = "skills",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER
//    )
//    private List<CharacterEntity> characterEntities;
}
