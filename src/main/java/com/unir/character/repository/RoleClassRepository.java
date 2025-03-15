package com.unir.character.repository;
import com.unir.character.model.RoleClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleClassRepository extends JpaRepository<RoleClass, Long> {
    Optional<RoleClass> findByName(String name);}
