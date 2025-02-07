package com.unir.roleapp.repository;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.entity.RoleClass;
import com.unir.roleapp.entity.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpellRepository extends JpaRepository<Spell, Long> {

    @Query("SELECT s FROM Spell s " +
            "JOIN s.roleClasses rc " +
            "WHERE s.level <= :level AND rc.name IN :roleClass")
    List<Spell> findSpellsByLevelAndRoleClass(
            @Param("level") int level,
            @Param("roleClass") String roleClass
    );
}
