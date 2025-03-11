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

    // Buscar un ítem específico por characterId y itemId
    @Query("SELECT ci FROM CharacterItem ci WHERE ci.character.id = :characterId AND ci.customItem.id = :customItemId")
    Optional<CharacterItem> findByCharacterIdAndCustomItemId(
            @Param("characterId") Long characterId,
            @Param("customItemId") Long customItemId
    );

    // Eliminar un ítem por characterId y itemId
    @Modifying
    @Transactional
    @Query("DELETE FROM CharacterItem ci WHERE ci.character.id = :characterId AND ci.customItem.id = :itemId")
    void deleteByCharacterIdAndItemId(
            @Param("characterId") Long characterId,
            @Param("itemId") Long itemId
    );

    // Upsert un ítem (insertar o actualizar) con updatedAt
    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO character_item (id_character, id_item, quantity, updated_at) " +
                    "VALUES (:characterId, :itemId, :quantity, :updatedAt) " +
                    "ON CONFLICT (id_character, id_item) DO UPDATE SET " +
                    "quantity = :quantity, updated_at = :updatedAt",
            nativeQuery = true
    )
    void upsertCharacterItem(
            @Param("characterId") Long characterId,
            @Param("itemId") Long itemId,
            @Param("quantity") int quantity,
            @Param("updatedAt") Long updatedAt
    );
}