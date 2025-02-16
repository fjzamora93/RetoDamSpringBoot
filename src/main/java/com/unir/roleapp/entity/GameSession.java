package com.unir.roleapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Cada sesión tenrá un único Usuario que será el Creador y el Game Máster
    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany( mappedBy = "gameSession", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private List<CharacterEntity>  characters;

    @OneToMany( mappedBy = "gameSession", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CustomItem>  customItems;


}
