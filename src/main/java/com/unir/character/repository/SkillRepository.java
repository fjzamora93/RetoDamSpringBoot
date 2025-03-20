package com.unir.character.repository;
import com.unir.character.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SkillRepository extends JpaRepository<Skill, Long> {


    @Modifying
    @Transactional
    @Query(
            value = """
        INSERT INTO character_skill (id_character, id_skill, value)
        VALUES (:characterId, :skillId, :value)
        ON CONFLICT (id_character, id_skill)
        DO UPDATE SET value = :value
        """,
            nativeQuery = true
    )
    void upsertSkills(
            @Param("characterId") Long characterId,
            @Param("skillId") Long skillId,
            @Param("value") int value
    );

}
