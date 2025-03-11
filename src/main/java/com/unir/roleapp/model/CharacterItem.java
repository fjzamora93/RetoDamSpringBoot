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
@NoArgsConstructor
public class CharacterItem {

    @EmbeddedId
    private CharacterItemId id;

    @Column(name = "quantity") private int quantity;
    @Column(name = "updated_at") private Long updatedAt;

    @ManyToOne
    @MapsId("characterId")
    @JoinColumn(name = "id_character")
    private CharacterEntity character;


    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "id_item")
    private CustomItem customItem;

    public CharacterItem(CharacterItemId id, int quantity, CharacterEntity character, CustomItem customItem) {
        this.id = id;
        this.quantity = quantity;
        this.character = character;
        this.customItem = customItem;
    }
}
