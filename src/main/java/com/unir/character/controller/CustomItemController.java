package com.unir.character.controller;

import com.unir.character.dto.CustomItemDTO;
import com.unir.character.enumm.ItemCategory;
import com.unir.character.service.CustomItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/items")
public class CustomItemController {

    @Autowired
    private CustomItemService customItemService;



    /** BUSCAR POR NOMBRE COMO PARÁMETRO EN LA URL */
    @GetMapping("/game-session/{id}")
    public  ResponseEntity<List<CustomItemDTO>>getItemsByGameSession(@PathVariable Long id) {
        List<CustomItemDTO> items = customItemService.getItemsByGameSession(id);
        return ResponseEntity.ok(items);

    }

    /** BUSCAR POR CATEGORÍA COMO PARÁMETRO EN LA URL */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<CustomItemDTO>> getItemsByCategory(@PathVariable String category) {
        ItemCategory itemCategory = ItemCategory.valueOf(category.toUpperCase());
        List<CustomItemDTO> items = customItemService.getItemsByCategory(itemCategory);
        return ResponseEntity.ok(items);
    }


}

