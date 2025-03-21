package com.unir.gestorvacantes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
