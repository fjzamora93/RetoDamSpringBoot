package com.unir.roleapp.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "character_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterItem {

    @EmbeddedId
    private CharacterItemId id;

    @Column(name = "quantity") private int quantity;

    @ManyToOne
    @MapsId("characterId")
    @JoinColumn(name = "id_character")
    private CharacterEntity character;


    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "id_item")
    private CustomItem customItem;

}
