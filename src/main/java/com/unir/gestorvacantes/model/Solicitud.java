package com.unir.gestorvacantes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unir.auth.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Integer idSolicitud;

    @Temporal(TemporalType.DATE)
    private String fecha;

    @Column(length = 100)
    private String archivo;

    @Column(length = 500)
    private String comentarios;

    private Integer estado;

    @ManyToOne
    @JoinColumn(name = "id_vacante")
    @JsonBackReference("vacante-solicitud")
    private Vacante vacante;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonBackReference("user-application")
    private User user;
}

