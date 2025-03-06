package com.unir.roleapp.service;

import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.roleapp.model.CharacterEntity;
import com.unir.roleapp.model.CustomItem;
import com.unir.roleapp.enumm.ItemCategory;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import com.unir.roleapp.repository.CharacterRepository;
import com.unir.roleapp.repository.CustomItemRepository;
import com.unir.roleapp.repository.ItemTemplateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
