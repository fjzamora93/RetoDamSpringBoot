package com.unir.roleapp.repository;

import com.unir.roleapp.model.CharacterEntity;
import com.unir.roleapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

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

    // BÚSQUEDA PERSONALIZADA QUE DEVUELVE UN RESUTLADO
    Optional<CharacterEntity> findByName(String name);
}





