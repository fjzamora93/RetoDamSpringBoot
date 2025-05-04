package com.unir.gestorvacantes.controller;


import com.unir.auth.model.User;
import com.unir.auth.service.UserService;
import com.unir.gestorvacantes.model.Empresa;
import com.unir.gestorvacantes.model.Vacante;
import com.unir.gestorvacantes.service.EmpresaService;
import com.unir.gestorvacantes.service.VacanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vacantes")
public class VacanteController {

    private final VacanteService vacanteService;
    private final UserService userService;
    private final EmpresaService empresaService;

    public VacanteController(VacanteService vacanteService, UserService userService, EmpresaService empresaService) {
        this.vacanteService = vacanteService;
        this.userService = userService;
        this.empresaService = empresaService;
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

    @PostMapping
    public ResponseEntity<Vacante> crearVacante(@RequestBody Vacante vacante) {
        User user = userService.getAuthenticatedUser();
        Empresa empresa = empresaService.obtenerEmpresaPorUsuario(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa no encontrada"));

        vacante.setEmpresa(empresa);
        Vacante nueva = vacanteService.guardarVacante(vacante);
        return ResponseEntity.ok(nueva);
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

    @GetMapping("/mis-vacantes")
    public List<Vacante> obtenerVacantesDeMiEmpresa() {
        User user = userService.getAuthenticatedUser();
        Empresa empresa = empresaService.obtenerEmpresaPorUsuario(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa no encontrada"));
        return vacanteService.findByEmpresa(empresa);
    }
}
