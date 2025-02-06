package com.unir.roleapp.controller;

import com.unir.roleapp.dto.ItemDTO;
import com.unir.roleapp.entity.Item;
import com.unir.roleapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    /** TODOS LOS OBJETOS */
    @GetMapping
    public List<ItemDTO> getAllItems() {
        return new ArrayList<>();
    }

}
