package com.unir.roleapp.entity;
import com.unir.roleapp.enumm.ItemCategory;
import com.unir.roleapp.enumm.StatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

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

}
