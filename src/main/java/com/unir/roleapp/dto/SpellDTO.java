package com.unir.roleapp.dto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


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
public class SpellDTO {
    private Long id;
    private String name;
    private String description;
    private int dice;
    private int level;
    private int cost;
    private String imgUrl;

    private List<String> roleClassesNames;

    // Generamos un constructor adicional pero sin la lista de Rol
    public SpellDTO(Long id, String name, String description,
                    int dice, int level, int cost, String imgUrl
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dice = dice;
        this.level = level;
        this.cost = cost;
        this.imgUrl = imgUrl;
    }
}
