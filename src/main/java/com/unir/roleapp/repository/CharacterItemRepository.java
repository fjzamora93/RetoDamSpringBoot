package com.unir.roleapp.repository;

import com.unir.roleapp.model.CharacterEntity;
import com.unir.roleapp.model.CharacterItem;
import com.unir.roleapp.model.CharacterItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterItemRepository extends JpaRepository<CharacterItem, CharacterItemId> {

    // Obtener todos los items de un personaje por su ID
    @Query("SELECT ci FROM CharacterItem ci WHERE ci.character.id = :characterId")
    List<CharacterItem> findByCharacterId(@Param("characterId") Long characterId);

    @Query("SELECT ci FROM CharacterItem ci WHERE ci.character.id = :characterId AND ci.customItem.id = :customItemId")
    Optional<CharacterItem> findByCharacterIdAndCustomItemId(@Param("characterId") Long characterId, @Param("customItemId") Long customItemId);


    // Eliminar un item de un personaje por IDs
    @Modifying
    @Transactional
    @Query("DELETE FROM CharacterItem ci WHERE ci.character.id = :characterId AND ci.customItem.id = :itemId")
    void deleteByCharacterIdAndItemId(@Param("characterId") Long characterId, @Param("itemId") Long itemId);


}
