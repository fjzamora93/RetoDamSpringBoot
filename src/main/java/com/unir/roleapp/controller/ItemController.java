package com.unir.roleapp.controller;

import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.roleapp.model.CharacterEntity;
import com.unir.roleapp.enumm.ItemCategory;
import com.unir.roleapp.service.CustomItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private CustomItemService customItemService;

    /** OBTENER TODOS LOS OBJETOS */
    @GetMapping
    public ResponseEntity<List<CustomItemDTO>> getAllItems() {
        System.out.print("Por lo menos llega la petción.......");
        List<CustomItemDTO> items = customItemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    /** BUSCAR POR NOMBRE COMO PARÁMETRO EN LA URL */
    @GetMapping("/name/{name}")
    public  ResponseEntity<List<CustomItemDTO>>getItemsByName(@PathVariable String name) {
        List<CustomItemDTO> items = customItemService.getItemsByName(name);
        return ResponseEntity.ok(items);

    }

    /** BUSCAR POR CATEGORÍA COMO PARÁMETRO EN LA URL */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<CustomItemDTO>> getItemsByCategory(@PathVariable String category) {
        ItemCategory itemCategory = ItemCategory.valueOf(category.toUpperCase());
        List<CustomItemDTO> items = customItemService.getItemsByCategory(itemCategory);
        return ResponseEntity.ok(items);
    }

    /** BUSCAR POR PRECIO MENOR O IGUAL A UN VALOR */
    @GetMapping("/goldvalue/{goldValue}")
    public ResponseEntity<List<CustomItemDTO>> getItemsByGoldValue(@PathVariable int goldValue) {
        List<CustomItemDTO> items = customItemService.getItemsByGoldValue(goldValue);
        return ResponseEntity.ok(items);
    }

    /** FILTROS COMBINADOS (OPCIONALES) Ejemplo:
     *
     * FIltrar por los tres campos:
     * http://localhost:8080/api/items/filter?name=Arco&category=WEAPON&goldValue=1000
     *
     * //MOstrar solo las armas que valen menos de 1000
     * http://localhost:8080/api/items/filter?&category=WEAPON&goldValue=1000
     * */
    @GetMapping("/filter")
    public ResponseEntity<List<CustomItemDTO>> getFilteredItems(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer goldValue) {

        ItemCategory itemCategory = (category != null) ? ItemCategory.valueOf(category.toUpperCase()) : null;
        List<CustomItemDTO> items =  customItemService.getFilteredItems(name, itemCategory, goldValue);
        return ResponseEntity.ok(items);
    }

    /** Añadir Item a Character */
    @PostMapping("/item")
    public ResponseEntity<CharacterEntity> addItemToCharacter(
            @RequestParam Long characterId,
            @RequestParam Long itemId
    ) {
        CharacterEntity updatedCharacter = customItemService.addItemToCharacter(characterId, itemId);
        return ResponseEntity.ok(updatedCharacter);
    }

    /** Eliminar Item de Character */
    @DeleteMapping("/item")
    public ResponseEntity<CharacterEntity> deleteItemFromCharacter(
            @RequestParam Long characterId,
            @RequestParam Long itemId) {
        CharacterEntity updatedCharacter = customItemService.deleteItemFromCharacter(characterId, itemId);
        return ResponseEntity.ok(updatedCharacter);
    }

}

