package com.unir.auth.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unir.auth.dto.UserDTO;
import com.unir.roleapp.model.CharacterEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Table(name = "user_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery( name = "Usuario.findAll", query = "FROM User" )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany( mappedBy = "user", fetch = FetchType.EAGER )
    @JsonManagedReference("user-character")
    private List<CharacterEntity> characterEntities;


    public UserDTO toDTO() {
        return  new UserDTO(
                this.id,
                this.name,
                this.email
        );
    }

}