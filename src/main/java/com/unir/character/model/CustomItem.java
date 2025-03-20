package com.unir.character.model;
import com.unir.character.dto.CustomItemDTO;
import com.unir.character.enumm.ItemCategory;
import com.unir.character.enumm.StatType;
import com.unir.adventure.model.GameSession;
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
    @Column(name = "dices_amount") private int dicesAmount;

    @Enumerated(EnumType.STRING)
    @Column(name= "modifiedstat") private StatType statType;
    @Column(name= "statvalue") private int statValue;

    // NO PODREMOS IDENTIFICAR LA SESSIÓN DE CADA OBJETO, AUNQUE LOS OBJETOS ESTARÁN TODOS DENTRO DE LA SESIÓN
    @ManyToOne
    @JoinColumn(name = "id_game_session")
    private GameSession gameSession;



    public CustomItemDTO toDTO() {
        return new CustomItemDTO(
                this.id,
                this.name,
                this.description,
                this.imgUrl,
                this.goldValue,
                this.category,
                this.dice,
                this.dicesAmount,
                this.statType,
                this.statValue,
                this.gameSession != null ? this.gameSession.getId() : 0
        );
    }


}
