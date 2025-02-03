package com.unir.roleapp.entity;
import java.util.ArrayList;
import java.util.List;


import com.unir.roleapp.enumm.Gender;
import com.unir.roleapp.enumm.Race;
import com.unir.roleapp.enumm.Range;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "character")
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
    @Column(name = "race") private Race race;
    @Column(name = "gender") private Gender gender;
    @Column(name = "size") private Range size;
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
    private List<Item> items = new ArrayList<>();


    // UNIDIRECCIONAL
    @ManyToMany( cascade= CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable(
            name = "character_skill",
            joinColumns = @JoinColumn( name = "id_character" ),
            inverseJoinColumns = @JoinColumn( name = "id_skill" )
    )
    private List<Skill> skills = new ArrayList<>();
}