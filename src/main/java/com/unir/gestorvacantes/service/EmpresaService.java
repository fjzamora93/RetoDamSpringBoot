package com.unir.gestorvacantes.service;


import com.unir.auth.model.User;
import com.unir.gestorvacantes.model.Empresa;
import com.unir.gestorvacantes.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    // Listar todas las empresas
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    // Obtener empresa por ID
    public Optional<Empresa> obtenerEmpresaPorId(Integer id) {
        return empresaRepository.findById(id);
    }

    // Crear o actualizar empresa
    public Empresa guardarEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    // Eliminar empresa
    public void eliminarEmpresa(Integer id) {
        empresaRepository.deleteById(id);
    }

    public Optional<Empresa> obtenerEmpresaPorUsuario(User user) {
        return empresaRepository.findByUser(user);
    }
}
