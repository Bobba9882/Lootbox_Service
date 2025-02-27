package com.studentgacha.lootbox.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
    private Long itemId;
    private String name;
    private int value;
    private double dropChance;
    private String description;

    public ItemDTO(Long itemId, String name, int value, double dropChance, String description) {
        this.itemId = itemId;
        this.name = name;
        this.value = value;
        this.dropChance = dropChance;
        this.description = description;
    }
}
