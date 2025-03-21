package com.unir.gestorvacantes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unir.auth.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "application")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "file", nullable = false)
    private String file;

    @Column(name = "comment")
    private String comment;

    @Column(name = "state", nullable = false)
    private int state;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Clave foránea en Application
    @JsonBackReference("user-application")
    private User user;

}
