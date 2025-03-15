package com.unir.character.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role_class")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name") private String name;




//    @OneToOne(mappedBy = "roleClass")
//    private CharacterEntity characterEntity;
}
