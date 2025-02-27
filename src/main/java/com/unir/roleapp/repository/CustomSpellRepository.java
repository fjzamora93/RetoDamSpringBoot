package com.unir.roleapp.repository;

import com.unir.roleapp.model.CustomSpell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomSpellRepository extends JpaRepository<CustomSpell, Long> {

    /** ESTO ES UN query derivation Y PERMITE REALIZAR CONSULTAS MÁS COMPLEJAS AUTOMÁTICAMENTE DESDE EL JPA
     *
     * - Aunque mi entidad "CustomSpell" no tiene el ID de Character, sino el Character completo,
     * la relación se maneja correctamente por debajo y buscará el ID al que se refiere.
     *
     * En resumen, la query de aquí abajo es la siguietne:
     *
     * SELECT * FROM custom_spell WHERE id_character = ?;
     * */
    List<CustomSpell> findByCharacter_Id(long characterId);
}
