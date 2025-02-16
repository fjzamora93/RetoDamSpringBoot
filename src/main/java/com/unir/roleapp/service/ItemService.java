package com.unir.roleapp.service;

import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.entity.CustomItem;
import com.unir.roleapp.enumm.ItemCategory;
import com.unir.roleapp.mapper.EntityToDtoMapper;
import com.unir.roleapp.repository.CharacterRepository;
import com.unir.roleapp.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired private EntityToDtoMapper entityToDtoMapper;
    @Autowired private ItemRepository itemRepository;
    @Autowired private CharacterRepository characterRepository;

    /**TODOS LOS OBJETOS*/
    public List<CustomItemDTO> getAllItems(){
        List<CustomItem> customItems = itemRepository.findAll();

        return customItems.stream()
                .map(item -> modelMapper.map(item, CustomItemDTO.class))
                .collect(Collectors.toList());
    }

    /** BUSCAR POR NOMBRE */
    public List<CustomItemDTO>  getItemsByName(String name){
        List<CustomItem> customItems = itemRepository.findByName(name);
        return customItems.stream()
                .map(item -> modelMapper.map(item, CustomItemDTO.class))
                .collect(Collectors.toList());
    }

    /** BUSCAR POR CATEGORY*/
    public List<CustomItemDTO>  getItemsByCategory(ItemCategory category){
        List<CustomItem> customItems = itemRepository.findByCategory(category);
        return customItems.stream()
                .map(item -> modelMapper.map(item, CustomItemDTO.class))
                .collect(Collectors.toList());
    }


    /** BUSCAR POR PRECIO (QUE VALGA MENOS QUE EL INT QUE SE LE PASE COMO PARÁMETRO*/
    public List<CustomItemDTO>  getItemsByGoldValue(int value){
        List<CustomItem> customItems = itemRepository.findByGoldValueLessThanEqual(value);
        return customItems.stream()
                .map(item -> modelMapper.map(item, CustomItemDTO.class))
                .collect(Collectors.toList());
    }


    /** Búsqueda con filtros avanzada*/
    public List<CustomItemDTO>  getFilteredItems(
            String name,
            ItemCategory category,
            int goldValue
    ){
        List <CustomItem> customItems = itemRepository.findFilteredItems(name, category, goldValue);
        return customItems.stream()
                .map(item -> modelMapper.map(item, CustomItemDTO.class))
                .collect(Collectors.toList());
    }

    /** AÑadir Item a Character */
    public CharacterEntity addItemToCharacter(Long characterId, Long itemId) {
        CharacterEntity character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        CustomItem customItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Agregar el item al personaje
        character.getCustomItems().add(customItem);

        // Guardar el personaje con el ítem añadido
        return characterRepository.save(character);
    }

    /** Eliminar Item de Character */
    public CharacterEntity deleteItemFromCharacter(Long characterId, Long itemId) {
        CharacterEntity character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        CustomItem customItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Eliminar el item del personaje
        character.getCustomItems().remove(customItem);

        // Guardar el personaje con el ítem eliminado
        return characterRepository.save(character);
    }

}
