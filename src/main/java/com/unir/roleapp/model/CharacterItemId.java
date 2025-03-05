package com.unir.roleapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterItemId implements Serializable {

    @Column(name = "id_character")
    private Long characterId;

    @Column(name = "id_item")
    private Long itemId;
}
