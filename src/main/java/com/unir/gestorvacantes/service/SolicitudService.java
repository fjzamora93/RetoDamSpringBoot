package com.unir.gestorvacantes.service;

import com.unir.gestorvacantes.model.Solicitud;
import com.unir.gestorvacantes.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudService {
    @Autowired
    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    // Listar todas las solicitudes
    public List<Solicitud> listarSolicitudes() {
        return solicitudRepository.findAll();
    }

    // Obtener solicitud por ID
    public List<Solicitud> obtenerSolicitudPorId(Integer id) {
        return solicitudRepository.findByUserId(id);
    }

    // Crear o actualizar solicitud
    public Solicitud guardarSolicitud(Solicitud solicitud) {
        return solicitudRepository.save(solicitud);
    }

    // Eliminar solicitud
    public void eliminarSolicitud(Integer id) {
        solicitudRepository.deleteById(id);
    }

    // Opcional: cancelar solicitud (cambiar estado)
    public void cancelarSolicitud(Integer id) {
        Optional<Solicitud> optionalSolicitud = solicitudRepository.findById(id);
        if (optionalSolicitud.isPresent()) {
            Solicitud solicitud = optionalSolicitud.get();
            solicitud.setEstado(0); // Por ejemplo: estado 0 = cancelado
            solicitudRepository.save(solicitud);
        }
    }
}
