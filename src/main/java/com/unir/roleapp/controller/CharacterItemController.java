package com.unir.roleapp.controller;


import com.unir.roleapp.dto.CharacterItemDTO;
import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.roleapp.service.CharacterItemService;
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
    public ResponseEntity<List<CharacterItemDTO>> getItemsByCharacterId(@PathVariable Long id) {
        List<CharacterItemDTO> items = characterItemService.getCustomItemsByCharacter(id);
        return ResponseEntity.ok(items);
    }

    @PostMapping()
    public ResponseEntity<CharacterItemDTO> addOrUpdateItemToCharacter(
            @RequestParam() Long characterId,
            @RequestBody CustomItemDTO customItemDTO,
            @RequestParam() int quantity){
        CharacterItemDTO item = characterItemService.addOrUpdateItemToCharacter(characterId, customItemDTO, quantity);
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
