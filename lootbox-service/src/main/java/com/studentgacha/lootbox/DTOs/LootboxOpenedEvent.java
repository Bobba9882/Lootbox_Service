package com.studentgacha.lootbox.DTOs;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class LootboxOpenedEvent implements Serializable {
    private Long userId;
    private Long itemId;

    public LootboxOpenedEvent(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "LootboxOpenedEvent{userId=" + userId + ", itemId=" + itemId + "}";
    }
}
