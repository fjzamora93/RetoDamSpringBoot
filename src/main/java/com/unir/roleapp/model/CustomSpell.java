package com.unir.roleapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "custom_spell")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomSpell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name") private String name;
    @Column(name = "description") private String description;
    @Column(name = "dice") private int dice;
    @Column(name = "level") private int level;
    @Column(name = "cost") private int cost;
    @Column(name = "img_url") private String imgUrl;


    // RELACIÓN UNIDIRECCIONAL, NO HAY ANOTACIÓN EN CHARACTER ENTITY
    @ManyToOne( cascade= CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn(name = "id_character")
    private CharacterEntity character;
}
