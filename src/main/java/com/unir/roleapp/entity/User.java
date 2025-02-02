package com.unir.roleapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery( name = "Usuario.findAll", query = "FROM User" )
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String nombreUsuario;

    @Column(name = "password")
    private String contraseña;

    @OneToMany( mappedBy = "user", fetch = FetchType.EAGER )
    private List<Character> characters;
}