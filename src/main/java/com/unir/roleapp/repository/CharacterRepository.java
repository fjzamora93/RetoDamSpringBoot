package com.unir.roleapp.repository;

import com.unir.roleapp.entity.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


/**
 * El repositorio implementa métodos de JPA automáticamente.
 * SI queremos incluir métodos que no están, como buscar por título, bastaría con hacer la anotación.
 *
 * Métodos disponibles:
 * - save()
 * - deleteById()
 * - findById()
 *
 * Cualquier otro, se puede crear con la anotación que vemos abajo para Title.
 * */


public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
    Optional<CharacterEntity> findByName(String name);
}





