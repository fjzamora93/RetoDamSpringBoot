package com.unir.character.dto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * La siguiente linea de código ayuda a que no haya recursividad al referirse una lista a la otra (ya que es un ManyToMany):
 *
 * @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
 *
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CustomSpellDTO {
    private Long id;
    private String name;
    private String description;
    private int dice;
    private int  dicesAmount;

    private int level;
    private int cost;
    private String imgUrl;

    private Long characterId;
}
