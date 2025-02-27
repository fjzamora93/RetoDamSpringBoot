package com.unir.roleapp.repository;
import com.unir.roleapp.model.RoleClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleClassRepository extends JpaRepository<RoleClass, Long> {
    Optional<RoleClass> findByName(String name);}
