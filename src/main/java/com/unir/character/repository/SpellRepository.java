package com.unir.character.repository;
import com.unir.character.model.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpellRepository extends JpaRepository<Spell, Long> {


    @Query("SELECT s FROM Spell s " +
            "JOIN s.roleClasses rc " +
            "WHERE s.level <= :level AND rc.name = :roleClass")
    List<Spell> findSpellsByLevelAndRoleClass(
            @Param("level") int level,
            @Param("roleClass") String roleClass
    );


}
