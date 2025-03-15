package com.unir.character.service;

import com.unir.character.dto.CustomItemDTO;
import com.unir.character.model.CustomItem;
import com.unir.character.enumm.ItemCategory;
import com.unir.character.repository.CharacterRepository;
import com.unir.character.repository.CustomItemRepository;
import com.unir.character.repository.ItemTemplateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomItemService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired private CustomItemRepository customItemRepository;
    @Autowired private ItemTemplateRepository itemTemplateRepository;
    @Autowired private CharacterRepository characterRepository;



    /** BUSCAR POR GAME SESSION */
    public List<CustomItemDTO>  getItemsByGameSession(Long id){
        List<CustomItem> customItems = customItemRepository.findByGameSession_Id(id);
        return customItems.stream()
                .map(CustomItem::toDTO)
                .collect(Collectors.toList());
    }



    /** BUSCAR POR CATEGORY*/
    public List<CustomItemDTO>  getItemsByCategory(ItemCategory category){
        List<CustomItem> customItems = customItemRepository.findByCategory(category);
        return customItems.stream()
                .map(CustomItem::toDTO)
                .collect(Collectors.toList());
    }


}
