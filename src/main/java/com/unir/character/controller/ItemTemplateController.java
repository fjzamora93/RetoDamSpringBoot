package com.unir.character.controller;


import com.unir.character.dto.ItemTemplateDTO;
import com.unir.character.service.ItemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items-template")
public class ItemTemplateController {

    @Autowired
    private ItemTemplateService itemTemplateService;


    @GetMapping
    public ResponseEntity<List<ItemTemplateDTO>> getAllItems() {
        System.out.print("Por lo menos llega la petción.......");
        List<ItemTemplateDTO> items = itemTemplateService.getAllItems();
        return ResponseEntity.ok(items);
    }
}