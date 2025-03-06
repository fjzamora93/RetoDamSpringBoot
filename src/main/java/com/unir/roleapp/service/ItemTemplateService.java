package com.unir.roleapp.service;


import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.roleapp.dto.ItemTemplateDTO;
import com.unir.roleapp.model.CustomItem;
import com.unir.roleapp.model.ItemTemplate;
import com.unir.roleapp.repository.CharacterRepository;
import com.unir.roleapp.repository.CustomItemRepository;
import com.unir.roleapp.repository.ItemTemplateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemTemplateService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ItemTemplateRepository itemTemplateRepository;


    public List<ItemTemplateDTO> getAllItems() {
        List<ItemTemplate> customItems = itemTemplateRepository.findAll();
        return customItems.stream()
                .map(item -> modelMapper.map(item, ItemTemplateDTO.class))
                .collect(Collectors.toList());
    }
}