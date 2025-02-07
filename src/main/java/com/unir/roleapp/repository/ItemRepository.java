package com.unir.roleapp.repository;
import com.unir.roleapp.entity.Item;
import com.unir.roleapp.enumm.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.name ILIKE %:name%")
    List<Item> findByName(@Param("name") String name);


    List<Item> findByCategory(ItemCategory category);

    // Buscar para que el valor en la columna gold_value de la tabla sea inferior al parámetro
    @Query("SELECT i FROM Item i WHERE i.goldValue <= :goldValue")
    List<Item> findByGoldValueLessThanEqual(
            @Param("goldValue") int goldValue
    );

    /** MÉTODO PERSONALIZADO PARA FILTROS OPCIONALES */
    @Query("SELECT i FROM Item i " +
            "WHERE (:name IS NULL OR i.name = :name) " +
            "AND (:category IS NULL OR i.category = :category) " +
            "AND (:goldValue IS NULL OR i.goldValue <= :goldValue)")
    List<Item> findFilteredItems(
            @Param("name") String name,
            @Param("category") ItemCategory category,
            @Param("goldValue") Integer goldValue);
}
