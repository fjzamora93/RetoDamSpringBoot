package com.unir.gestorvacantes.repository;

import com.unir.gestorvacantes.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {

    // Buscar solicitudes a parrtir de la id del usuario
    List<Solicitud> findByUserId(Integer userId);

    List<Solicitud> findByVacanteIdVacante(Integer idVacante);

}
