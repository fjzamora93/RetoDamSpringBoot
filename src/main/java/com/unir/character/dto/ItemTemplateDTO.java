package com.unir.character.dto;

import com.unir.character.enumm.ItemCategory;
import com.unir.character.enumm.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemTemplateDTO {
    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private int goldValue;
    private ItemCategory category;
    private int dice;

    private StatType statType;
    private int statValue;

}
