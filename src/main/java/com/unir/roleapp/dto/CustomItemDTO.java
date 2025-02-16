package com.unir.roleapp.dto;
import com.unir.roleapp.entity.GameSession;
import com.unir.roleapp.enumm.ItemCategory;
import com.unir.roleapp.enumm.StatType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomItemDTO {
    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private int goldValue;
    private ItemCategory category;
    private int dice;
    
    private StatType statType;
    private int statValue;

//    RELACIÓN UNIDIRECCIONAL, NO HACE FALTA INDICAR LA SESSIÓN A LA QUE PERTENECE
//    private GameSession gameSession;

}
