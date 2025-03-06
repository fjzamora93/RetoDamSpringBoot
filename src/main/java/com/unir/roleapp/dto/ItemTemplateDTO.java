package com.unir.roleapp.dto;

import com.unir.roleapp.enumm.ItemCategory;
import com.unir.roleapp.enumm.StatType;
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
