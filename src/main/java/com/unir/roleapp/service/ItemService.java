package com.unir.roleapp.service;

import com.unir.roleapp.dto.ItemDTO;
import com.unir.roleapp.entity.CharacterEntity;
import com.unir.roleapp.entity.Item;
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
    public List<ItemDTO> getAllItems(){
        List<Item> items = itemRepository.findAll();

        return items.stream()
                .map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    /** BUSCAR POR NOMBRE */
    public List<ItemDTO>  getItemsByName(String name){
        List<Item> items = itemRepository.findByName(name);
        return items.stream()
                .map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    /** BUSCAR POR CATEGORY*/
    public List<ItemDTO>  getItemsByCategory(ItemCategory category){
        List<Item> items = itemRepository.findByCategory(category);
        return items.stream()
                .map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());
    }


    /** BUSCAR POR PRECIO (QUE VALGA MENOS QUE EL INT QUE SE LE PASE COMO PARÁMETRO*/
    public List<ItemDTO>  getItemsByGoldValue(int value){
        List<Item> items = itemRepository.findByGoldValueLessThanEqual(value);
        return items.stream()
                .map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());
    }


    /** Búsqueda con filtros avanzada*/
    public List<ItemDTO>  getFilteredItems(
            String name,
            ItemCategory category,
            int goldValue
    ){
        List <Item> items = itemRepository.findFilteredItems(name, category, goldValue);
        return items.stream()
                .map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    /** AÑadir Item a Character */
    public CharacterEntity addItemToCharacter(Long characterId, Long itemId) {
        CharacterEntity character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Agregar el item al personaje
        character.getItems().add(item);

        // Guardar el personaje con el ítem añadido
        return characterRepository.save(character);
    }

    /** Eliminar Item de Character */
    public CharacterEntity deleteItemFromCharacter(Long characterId, Long itemId) {
        CharacterEntity character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Eliminar el item del personaje
        character.getItems().remove(item);

        // Guardar el personaje con el ítem eliminado
        return characterRepository.save(character);
    }

}
