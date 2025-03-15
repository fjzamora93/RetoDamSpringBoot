package com.unir.character.repository;

import com.unir.character.model.CharacterEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    // BÚSQUEDA PERSONALIZADA QUE DEUVVLEVE UNA LISTA
    List<CharacterEntity> findByUser_Id(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CharacterEntity c WHERE c.id = :id")
    Optional<CharacterEntity> findByIdWithLock(@Param("id") Long id);

    // BÚSQUEDA PERSONALIZADA QUE DEVUELVE UN RESUTLADO
    Optional<CharacterEntity> findByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM CharacterEntity c WHERE c.id = :id")
    void deleteById(Long id);
}





