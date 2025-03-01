package com.unir.roleapp.model;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unir.roleapp.enumm.Gender;
import com.unir.roleapp.enumm.Race;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")  private String name;
    @Column(name = "description")  private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "race") private Race race;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender") private Gender gender;

    @Column(name = "size") private int size;
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

    //UNIDIRECCIONAL
    @ManyToMany( cascade= CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable(
            name = "character_item",
            joinColumns = @JoinColumn( name = "id_character" ),
            inverseJoinColumns = @JoinColumn( name = "id_item" )
    )
    private List<CustomItem> customItems = new ArrayList<>();


    // UNIDIRECCIONAL
    @ManyToMany( cascade= CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable(
            name = "character_skill",
            joinColumns = @JoinColumn( name = "id_character" ),
            inverseJoinColumns = @JoinColumn( name = "id_skill" )
    )
    private List<Skill> skills = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "id_game_session")
    @JsonIgnore
    private GameSession gameSession;

}