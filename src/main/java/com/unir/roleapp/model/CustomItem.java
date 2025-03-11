package com.unir.roleapp.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unir.roleapp.dto.CustomItemDTO;
import com.unir.roleapp.enumm.ItemCategory;
import com.unir.roleapp.enumm.StatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "custom_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name") private String name;
    @Column(name = "description") private String description;
    @Column(name = "img_url") private String imgUrl;
    @Column(name = "gold_value") private int goldValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "category") private ItemCategory category;
    @Column(name = "dice") private int dice;

    @Enumerated(EnumType.STRING)
    @Column(name= "modifiedstat") private StatType statType;
    @Column(name= "statvalue") private int statValue;

    // NO PODREMOS IDENTIFICAR LA SESSIÓN DE CADA OBJETO, AUNQUE LOS OBJETOS ESTARÁN TODOS DENTRO DE LA SESIÓN
    @ManyToOne
    @JoinColumn(name = "id_game_session")
    private GameSession gameSession;

    public static CustomItem fromDTO(
            CustomItemDTO dto,
            GameSession gameSession
    ) {
        CustomItem customItem = new CustomItem();
        customItem.setId(dto.getId());
        customItem.setName(dto.getName());
        customItem.setDescription(dto.getDescription());
        customItem.setImgUrl(dto.getImgUrl());
        customItem.setGoldValue(dto.getGoldValue());
        customItem.setCategory(dto.getCategory());
        customItem.setDice(dto.getDice());
        customItem.setStatType(dto.getStatType());
        customItem.setStatValue(dto.getStatValue());
        customItem.setGameSession(gameSession);

        return customItem;
    }

    public CustomItemDTO toDTO() {
        return new CustomItemDTO(
                this.id,
                this.name,
                this.description,
                this.imgUrl,
                this.goldValue,
                this.category,
                this.dice,
                this.statType,
                this.statValue,
                this.gameSession != null ? this.gameSession.getId() : 0
        );
    }


}
