package com.studentgacha.lootbox.service;

public interface LootboxEventPublisher {
    void publishLootboxOpened(Long userId, Long itemId);
}
