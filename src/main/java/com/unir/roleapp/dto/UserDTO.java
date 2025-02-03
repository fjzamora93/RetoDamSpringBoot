package com.unir.roleapp.dto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String nombreUsuario;
    private String contraseña;

    // Relación con CharacterEntity (se pasa la lista de characterEntities)
    private List<CharacterResponseDTO> characterEntities;
}
