package com.studentgacha.lootbox.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LootboxOpenedEventDTO implements Serializable {
    private Long userId;
    private Long itemId;

    public LootboxOpenedEventDTO(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "LootboxOpenedEvent{userId=" + userId + ", itemId=" + itemId + "}";
    }
}
