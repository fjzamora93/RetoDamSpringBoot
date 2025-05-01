package com.unir.gestorvacantes.controller;


import com.unir.gestorvacantes.model.Solicitud;
import com.unir.gestorvacantes.service.SolicitudService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    // Listar todas las solicitudes
    @GetMapping
    public List<Solicitud> listarSolicitudes() {
        return solicitudService.listarSolicitudes();
    }


    // Obtener solicitud por la ID del usuario
    @GetMapping("/{id}")
    public List<Solicitud> obtenerSolicitud(@PathVariable Integer id) {
        return solicitudService.obtenerSolicitudPorId(id);
    }

    // Crear nueva solicitud
    @PostMapping
    public Solicitud crearSolicitud(@RequestBody Solicitud solicitud) {
        return solicitudService.guardarSolicitud(solicitud);
    }

    // Actualizar solicitud
    @PutMapping("/{id}")
    public Solicitud actualizarSolicitud(@PathVariable Integer id, @RequestBody Solicitud solicitudActualizada) {
        solicitudActualizada.setIdSolicitud(id);
        return solicitudService.guardarSolicitud(solicitudActualizada);
    }

    // Eliminar solicitud
    @DeleteMapping("/{id}")
    public void eliminarSolicitud(@PathVariable Integer id) {
        solicitudService.eliminarSolicitud(id);
    }


    // Cancelar solicitud (cambiando estado)
    @PutMapping("/{id}/cancelar")
    public void cancelarSolicitud(@PathVariable Integer id) {
        solicitudService.cancelarSolicitud(id);
    }
}
