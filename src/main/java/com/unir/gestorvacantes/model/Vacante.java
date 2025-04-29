package com.unir.gestorvacantes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vacantes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vacante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vacante")
    private Integer idVacante; // PK

    @Column(length = 45)
    private String nombre;

    @Column(length = 2000)
    private String descripcion;

    @Column()
    private String fecha;

    private Double salario;

    @Column(length = 45)
    private String estatus;

    @Column()
    private int destacado;

    @Column(length = 100)
    private String imagen;

    @Column(length = 100)
    private String detalles;

    // Relaciones:

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @OneToMany(mappedBy = "vacante")
    @JsonManagedReference("vacante-solicitud")
    private List<Solicitud> solicitudes = new ArrayList<>();

}
