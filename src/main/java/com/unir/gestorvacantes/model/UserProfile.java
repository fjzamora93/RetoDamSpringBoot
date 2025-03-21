package com.unir.gestorvacantes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unir.auth.model.User;
import com.unir.gestorvacantes.dto.UserProfileDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // Cada UsuarioPErfil solo puede tener un Usuario y un solo perfil
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-profile")
    private User user;

    public UserProfile(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public UserProfileDTO toUserProfileDto() {
        return new UserProfileDTO(
                this.id,
                this.user.getId(),
                this.name
        );
    }




}
