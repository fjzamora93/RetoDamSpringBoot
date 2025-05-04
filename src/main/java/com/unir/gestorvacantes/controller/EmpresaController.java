package com.unir.gestorvacantes.controller;


import com.unir.auth.model.User;
import com.unir.auth.service.UserService;
import com.unir.gestorvacantes.model.Empresa;
import com.unir.gestorvacantes.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final UserService userService;

    public EmpresaController(EmpresaService empresaService, UserService userService) {
        this.empresaService = empresaService;
        this.userService = userService;
    }

    // Listar todas las empresas
    @GetMapping
    public List<Empresa> listarEmpresas() {
        return empresaService.listarEmpresas();
    }

    // Obtener empresa por ID
    @GetMapping("/{id}")
    public Optional<Empresa> obtenerEmpresa(@PathVariable Integer id) {
        return empresaService.obtenerEmpresaPorId(id);
    }

    // Crear nueva empresa
    @PostMapping
    public Empresa crearEmpresa(@RequestBody Empresa empresa) {
        return empresaService.guardarEmpresa(empresa);
    }

    // Actualizar empresa
    @PutMapping("/{id}")
    public Empresa actualizarEmpresa(@PathVariable Integer id, @RequestBody Empresa empresaActualizada) {
        empresaActualizada.setIdEmpresa(id);
        return empresaService.guardarEmpresa(empresaActualizada);
    }

    // Eliminar empresa
    @DeleteMapping("/{id}")
    public void eliminarEmpresa(@PathVariable Integer id) {
        empresaService.eliminarEmpresa(id);
    }

    @GetMapping("/mi-empresa")
    public Empresa obtenerMiEmpresa() {
        User user = userService.getAuthenticatedUser(); // o similar
        return empresaService.obtenerEmpresaPorUsuario(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No tienes una empresa asociada"));
    }
}
