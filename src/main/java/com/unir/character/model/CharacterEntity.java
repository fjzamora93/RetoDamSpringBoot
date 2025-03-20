package com.unir.character.model;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unir.auth.model.User;
import com.unir.character.enumm.Gender;
import com.unir.character.enumm.Race;
import com.unir.adventure.model.GameSession;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "character_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @Column(name = "name")  private String name;
    @Column(name = "description")  private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "race") private Race race;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender") private Gender gender;

    @Column(name = "armor") private int armor;
    @Column(name = "level") private int level;
    @Column(name = "age") private int age;
    @Column(name = "gold") private int gold;
    @Column(name = "strength") private int strength;
    @Column(name = "dexterity") private int dexterity;
    @Column(name = "constitution") private int constitution;
    @Column(name = "intelligence") private int intelligence;
    @Column(name = "wisdom") private int wisdom;
    @Column(name = "charisma") private int charisma;
    @Column(name = "img_url") private String imgUrl;


    // RELACION BIDIRECCIONAL
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonBackReference("user-character")
    private User user;


    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn(name = "id_roleclass")
    private RoleClass roleClass;



    // UNIDIRECCIONAL
    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CharacterSkill> characterSkills = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "id_game_session")
    @JsonBackReference("character-session")
    private GameSession gameSession;

    @Version
    @Column(name = "version")
    private Long version = 0L;

}