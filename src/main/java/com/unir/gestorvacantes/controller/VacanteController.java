package com.unir.gestorvacantes.controller;


import com.unir.gestorvacantes.model.Vacante;
import com.unir.gestorvacantes.service.VacanteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vacantes")
public class VacanteController {

    private final VacanteService vacanteService;

    public VacanteController(VacanteService vacanteService) {
        this.vacanteService = vacanteService;
    }

    // Listar todas las vacantes
    @GetMapping
    public List<Vacante> listarVacantes() {
        return vacanteService.listarVacantes();
    }

    // Obtener vacante por ID
    @GetMapping("/{id}")
    public Optional<Vacante> obtenerVacante(@PathVariable Integer id) {
        return vacanteService.obtenerVacantePorId(id);
    }

    // Crear nueva vacante
    @PostMapping
    public Vacante crearVacante(@RequestBody Vacante vacante) {
        return vacanteService.guardarVacante(vacante);
    }

    // Actualizar vacante
    @PutMapping("/{id}")
    public Vacante actualizarVacante(@PathVariable Integer id, @RequestBody Vacante vacanteActualizada) {
        vacanteActualizada.setIdVacante(id);
        return vacanteService.guardarVacante(vacanteActualizada);
    }

    // Eliminar vacante
    @DeleteMapping("/{id}")
    public void eliminarVacante(@PathVariable Integer id) {
        vacanteService.eliminarVacante(id);
    }

    // Cancelar vacante (cambiar estado a "CANCELADA")
    @PutMapping("/{id}/cancelar")
    public void cancelarVacante(@PathVariable Integer id) {
        vacanteService.cancelarVacante(id);
    }
}
