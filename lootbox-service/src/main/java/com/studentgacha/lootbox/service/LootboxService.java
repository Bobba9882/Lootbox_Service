package com.studentgacha.lootbox.service;

import com.studentgacha.lootbox.model.Item;
import com.studentgacha.lootbox.model.Lootbox;

import java.util.List;
import java.util.Map;

public interface LootboxService {
    // Lootbox Operations
    Lootbox createLootbox(Lootbox lootbox);
    Lootbox updateLootbox(Long lootboxId, Lootbox updatedLootbox);
    void deleteLootbox(Long lootboxId);
    List<Lootbox> getAllLootboxes();
    List<Item> getLootboxContents(Long lootboxId);

    // Item Operations
    List<Item> getItemsByIds(List<Long> ids);
    Item addItemToLootbox(Long lootboxId, Item item);
    Item updateItem(Long itemId, Item updatedItem);
    void deleteItem(Long itemId);

    // Lootbox Opening Mechanics
    Item openLootbox(Long userId, Long lootboxId);
    Map<String, Integer> openLootboxMultipleTimes(Long lootboxId, int pulls);
}
