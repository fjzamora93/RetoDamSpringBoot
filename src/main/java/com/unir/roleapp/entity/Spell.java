package com.unir.roleapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "spell")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Spell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name") private String name;
    @Column(name = "description") private String description;
    @Column(name = "dice") private int dice;
    @Column(name = "level") private int level;
    @Column(name = "cost") private int cost;
    @Column(name = "img_url") private String imgUrl;


    // BIDIRECCIONAL
    @ManyToMany( cascade= CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable(
            name = "spell_role_class",
            joinColumns = @JoinColumn( name = "id_spell" ),
            inverseJoinColumns = @JoinColumn( name = "id_role_class" )
    )
    private List<RoleClass> roleClasses;
}
