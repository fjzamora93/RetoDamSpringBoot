package com.unir.roleapp.dto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class RoleClassDTO {
    private Long id;
    private String name;
}
