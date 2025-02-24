package com.unir.roleapp.repository;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.entity.CustomSpell;
import com.unir.roleapp.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<List<Skill>> findByCharacter_Id(long characterId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO character_skill (id_character, id_skill) VALUES " +
            "(?1, ?2), (?1, ?3), (?1, ?4)", nativeQuery = true)
    void addDefaultSKills(Long characterId, Long skillId1, Long skillId2, Long skillId3);
}
