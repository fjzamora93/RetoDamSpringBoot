package com.unir.roleapp.controller;


import com.unir.roleapp.dto.CharacterItemResponseDTO;
import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.roleapp.model.CharacterItem;
import com.unir.roleapp.service.CharacterItemService;
import com.unir.roleapp.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/custom-items")
public class CharacterItemController {


    @Autowired
    private CharacterItemService characterItemService;


    @GetMapping("/character/{id}")
    public ResponseEntity<List<CharacterItemResponseDTO>> getItemsByCharacterId(@PathVariable Long id) {
        List<CharacterItemResponseDTO> items = characterItemService.getCustomItemsByCharacter(id);
        return ResponseEntity.ok(items);
    }

    @PostMapping()
    public ResponseEntity<CharacterItemResponseDTO> addOrUpdateItemToCharacter(
            @RequestParam(required = true) Long characterId,
            @RequestBody CustomItemDTO customItemDTO,
            @RequestParam(required = true) int quantity){
        CharacterItemResponseDTO item = characterItemService.addOrUpdateItemToCharacter(characterId, customItemDTO, quantity);
        return ResponseEntity.ok(item);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteItemFromCharacter(
        @RequestParam(required = true) Long characterId,
        @RequestParam(required = true) Long itemId
    ){
        characterItemService.deleteItemFromCharacter(characterId, itemId);
        return ResponseEntity.noContent().build();
    }


}
