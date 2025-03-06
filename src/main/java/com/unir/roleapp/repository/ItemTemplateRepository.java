package com.unir.roleapp.repository;

import com.unir.roleapp.model.CharacterItemId;
import com.unir.roleapp.model.ItemTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTemplateRepository extends JpaRepository<ItemTemplate, CharacterItemId> {

}