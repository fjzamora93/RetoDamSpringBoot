package com.unir.roleapp.dto;
import com.unir.roleapp.enumm.ItemCategory;
import com.unir.roleapp.enumm.StatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
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
