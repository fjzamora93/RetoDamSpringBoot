package com.unir.roleapp.controller;

import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.roleapp.enumm.ItemCategory;
import com.unir.roleapp.service.CustomItemService;
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

