package com.unir.roleapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "game_session")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Cada sesión tenrá un único Usuario que será el Creador y el Game Máster
    @ManyToOne( cascade = CascadeType.MERGE ,  fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany( mappedBy = "gameSession", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private List<CharacterEntity>  characters;

    @OneToMany( mappedBy = "gameSession", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CustomItem>  customItems;

    public GameSession(User user) {
        this.user = user;
    }

}
