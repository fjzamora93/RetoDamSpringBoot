package com.unir.gestorvacantes.repository;

import com.unir.auth.model.User;
import com.unir.gestorvacantes.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Optional<Empresa> findByUser(User user);
}
