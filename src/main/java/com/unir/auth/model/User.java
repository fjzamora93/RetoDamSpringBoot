package com.unir.auth.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unir.auth.dto.UserDTO;
import com.unir.auth.enumm.UserRole;
import com.unir.gestorvacantes.model.Solicitud;
import com.unir.gestorvacantes.model.UserProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "user_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private int enabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference("user-profile")
    private List<UserProfile> profiles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-application")
    private List<Solicitud> solicitudes = new ArrayList<>();



    public User(Long id, String name, String surname, String email, String password, int enabled,  UserRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }


    public UserDTO toDTO() {
        return  new UserDTO(
                this.id,
                this.name,
                this.surname,
                this.email,
                this.password,
                this.enabled,
                this.role
        );
    }

}