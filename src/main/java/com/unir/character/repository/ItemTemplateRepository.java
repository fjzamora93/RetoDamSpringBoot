package com.unir.character.repository;

import com.unir.character.model.CharacterItemId;
import com.unir.character.model.ItemTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTemplateRepository extends JpaRepository<ItemTemplate, CharacterItemId> {

}