package com.unir.roleapp.controller;

import com.unir.roleapp.dto.ItemDTO;
import com.unir.roleapp.entity.Item;
import com.unir.roleapp.enumm.ItemCategory;
import com.unir.roleapp.repository.ItemRepository;
import com.unir.roleapp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /** OBTENER TODOS LOS OBJETOS */
    @GetMapping
    public List<ItemDTO> getAllItems() {
        return itemService.getAllItems();
    }

    /** BUSCAR POR NOMBRE COMO PARÁMETRO EN LA URL */
    @GetMapping("/name/{name}")
    public List<ItemDTO> getItemsByName(@PathVariable String name) {
        return itemService.getItemsByName(name);
    }

    /** BUSCAR POR CATEGORÍA COMO PARÁMETRO EN LA URL */
    @GetMapping("/category/{category}")
    public List<ItemDTO> getItemsByCategory(@PathVariable String category) {
        ItemCategory itemCategory = ItemCategory.valueOf(category.toUpperCase());
        return itemService.getItemsByCategory(itemCategory);
    }

    /** BUSCAR POR PRECIO MENOR O IGUAL A UN VALOR */
    @GetMapping("/goldvalue/{goldValue}")
    public List<ItemDTO> getItemsByGoldValue(@PathVariable int goldValue) {
        return itemService.getItemsByGoldValue(goldValue);
    }

    /** FILTROS COMBINADOS (OPCIONALES) */
//    @GetMapping("/filter")
//    public List<ItemDTO> getFilteredItems(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) String category,
//            @RequestParam(required = false) Integer goldValue) {
//
//        ItemCategory itemCategory = (category != null) ? ItemCategory.valueOf(category.toUpperCase()) : null;
//        return itemService.getFilteredItems(name, itemCategory, goldValue);
//    }
}

