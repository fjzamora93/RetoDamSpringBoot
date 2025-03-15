package com.unir.adventure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unir.auth.model.User;
import com.unir.character.model.CharacterEntity;
import com.unir.character.model.CustomItem;
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
    @JsonManagedReference("character-session")
    private List<CharacterEntity>  characters;


    @OneToMany( mappedBy = "gameSession", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<CustomItem>  customItems;



    public GameSession(User user) {
        this.user = user;
    }

}
