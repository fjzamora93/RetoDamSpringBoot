package com.unir.roleapp.repository;
import com.unir.roleapp.model.CustomItem;
import com.unir.roleapp.enumm.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<CustomItem, Long> {
    @Query("SELECT i FROM CustomItem i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<CustomItem> findByName(@Param("name") String name);


    List<CustomItem> findByCategory(ItemCategory category);

    // Buscar para que el valor en la columna gold_value de la tabla sea inferior al parámetro
    @Query("SELECT i FROM CustomItem i WHERE i.goldValue <= :goldValue")
    List<CustomItem> findByGoldValueLessThanEqual(
            @Param("goldValue") int goldValue
    );


    /** MÉTODO PERSONALIZADO PARA FILTROS OPCIONALES */
    @Query("SELECT i FROM CustomItem i " +
            "WHERE (:name IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:category IS NULL OR i.category = :category) " +
            "AND (:goldValue IS NULL OR i.goldValue <= :goldValue)")

    List<CustomItem> findFilteredItems(
            @Param("name") String name,
            @Param("category") ItemCategory category,
            @Param("goldValue") Integer goldValue);
}
