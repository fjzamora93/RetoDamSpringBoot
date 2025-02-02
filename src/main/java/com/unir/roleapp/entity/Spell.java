package com.unir.roleapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private int id;

    @ManyToMany(
            mappedBy = "spells",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Character> characters;

}
