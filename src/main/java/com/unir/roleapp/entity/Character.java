package com.unir.roleapp.entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



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
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;






    // ESTABLECEMOS LAS RELACIONES
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany( cascade= CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable(
            name = "character_item",
            joinColumns = @JoinColumn( name = "id_character" ),
            inverseJoinColumns = @JoinColumn( name = "id_item" )
    )
    private List<Item> items = new ArrayList<>();



    @ManyToMany( cascade= CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable(
            name = "character_spell",
            joinColumns = @JoinColumn( name = "id_character" ),
            inverseJoinColumns = @JoinColumn( name = "id_spell" )
    )
    private List<Spell> spells = new ArrayList<>();

    @ManyToMany( cascade= CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable(
            name = "character_skill",
            joinColumns = @JoinColumn( name = "id_character" ),
            inverseJoinColumns = @JoinColumn( name = "id_skill" )
    )
    private List<Skill> skills = new ArrayList<>();


}