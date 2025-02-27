package com.studentgacha.lootbox.service.impl;

import com.studentgacha.lootbox.model.Item;
import com.studentgacha.lootbox.model.Lootbox;
import com.studentgacha.lootbox.repository.LootboxRepository;
import com.studentgacha.lootbox.service.LootboxService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class LootboxServiceImpl implements LootboxService {

    private final LootboxRepository lootboxRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public LootboxServiceImpl(LootboxRepository lootboxRepository) {
        this.lootboxRepository = lootboxRepository;
    }

    @Override
    public Item openLootbox(Long lootboxId) {

        Lootbox lootbox = lootboxRepository.findById(lootboxId)
                .orElseThrow(() -> new RuntimeException("Lootbox not found"));

        List<Item> items = lootbox.getItems();
        if (items.isEmpty()) {
            throw new RuntimeException("No items found in this lootbox");
        }

        return getRandomItem(items);
    }

    public Item getRandomItem(List<Item> items) {
        double totalWeight = items.stream().mapToDouble(Item::getDropChance).sum();
        double randomValue = secureRandom.nextDouble() * totalWeight;

        double cumulativeWeight = 0.0;
        for (Item item : items) {
            cumulativeWeight += item.getDropChance();
            if (randomValue <= cumulativeWeight) {
                return item;
            }
        }

        return items.get(items.size() - 1);
    }
}
