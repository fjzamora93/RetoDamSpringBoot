package com.unir.gestorvacantes.service;


import com.unir.gestorvacantes.model.Empresa;
import com.unir.gestorvacantes.model.Vacante;
import com.unir.gestorvacantes.repository.VacanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacanteService {

    private final VacanteRepository vacanteRepository;

    public VacanteService(VacanteRepository vacanteRepository) {
        this.vacanteRepository = vacanteRepository;
    }

    // Listar todas las vacantes
    public List<Vacante> listarVacantes() {
        return vacanteRepository.findAll();
    }

    // Obtener vacante por ID
    public Optional<Vacante> obtenerVacantePorId(Integer id) {
        return vacanteRepository.findById(id);
    }

    // Crear o actualizar vacante
    public Vacante guardarVacante(Vacante vacante) {
        return vacanteRepository.save(vacante);
    }

    // Eliminar vacante
    public void eliminarVacante(Integer id) {
        vacanteRepository.deleteById(id);
    }

    // Cancelar vacante (opcional: cambiar estado a "CANCELADA")
    public void cancelarVacante(Integer id) {
        Optional<Vacante> optionalVacante = vacanteRepository.findById(id);
        if (optionalVacante.isPresent()) {
            Vacante vacante = optionalVacante.get();
            vacante.setEstatus("CANCELADA");
            vacanteRepository.save(vacante);
        }
    }

    public List<Vacante> findByEmpresa(Empresa empresa) {
        return vacanteRepository.findByEmpresa(empresa);
    }
}
