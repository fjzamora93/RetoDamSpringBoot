package com.unir.roleapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Entity
@Table(name = "user_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery( name = "Usuario.findAll", query = "FROM User" )
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String nombreUsuario;

    @Column(name = "password")
    private String contraseña;

    @OneToMany( mappedBy = "user", fetch = FetchType.EAGER )
    private List<CharacterEntity> characterEntities;
}