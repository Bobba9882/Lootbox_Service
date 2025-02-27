package com.studentgacha.lootbox.service;

import com.studentgacha.lootbox.model.Item;

import java.util.Map;

public interface LootboxService {
    Item openLootbox(Long userId, Long lootboxId);
    Map<String, Integer> openLootboxMultipleTimes(Long lootboxId, int pulls);
}
