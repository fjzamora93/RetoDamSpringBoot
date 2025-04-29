package com.unir.gestorvacantes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Integer idEmpresa; // PK

    @Column(name = "razon_social", length = 45)
    private String razonSocial;

    @Column(name = "direccion_fiscal", length = 45)
    private String direccionFiscal;

    @Column(length = 45)
    private String pais;

    // Relaciones:

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vacante> vacantes;

}
