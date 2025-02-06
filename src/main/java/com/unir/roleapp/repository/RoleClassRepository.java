package com.unir.roleapp.repository;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.entity.RoleClass;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleClassRepository extends JpaRepository<RoleClass, Long> {
}
