package com.studentgacha.lootbox.service.impl;

import com.studentgacha.lootbox.model.Item;
import com.studentgacha.lootbox.model.Lootbox;
import com.studentgacha.lootbox.repository.LootboxRepository;
import com.studentgacha.lootbox.service.LootboxEventPublisher;
import com.studentgacha.lootbox.service.LootboxService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class LootboxServiceImpl implements LootboxService {

    private final LootboxEventPublisher eventPublisher;
    private final LootboxRepository lootboxRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public LootboxServiceImpl(LootboxRepository lootboxRepository, LootboxEventPublisher eventPublisher) {
        this.lootboxRepository = lootboxRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Item openLootbox(Long userId, Long lootboxId) {

        Lootbox lootbox = lootboxRepository.findById(lootboxId)
                .orElseThrow(() -> new RuntimeException("Lootbox not found"));

        List<Item> items = lootbox.getItems();
        if (items.isEmpty()) {
            throw new RuntimeException("No items found in this lootbox");
        }

        Item obtainedItem = getRandomItem(items);

        // Publish the event to RabbitMQ
        eventPublisher.publishLootboxOpened(userId, obtainedItem.getId());
        return obtainedItem;
    }

    private Item getRandomItem(List<Item> items) {
        double totalWeight = items.stream().mapToDouble(Item::getDropChance).sum();
        double randomValue = ThreadLocalRandom.current().nextDouble() * totalWeight;
        randomValue += secureRandom.nextDouble() * 0.00001;  // Add entropy for randomness

        double cumulativeWeight = 0.0;
        for (Item item : items) {
            cumulativeWeight += item.getDropChance();
            if (randomValue <= cumulativeWeight) {
                return item;
            }
        }

        return items.get(items.size() - 1); // Fallback (should never happen)
    }

    public Map<String, Integer> openLootboxMultipleTimes(Long lootboxId, int pulls) {
        Lootbox lootbox = lootboxRepository.findById(lootboxId)
                .orElseThrow(() -> new RuntimeException("Lootbox not found"));

        List<Item> items = lootbox.getItems();
        if (items.isEmpty()) {
            throw new RuntimeException("No items found in this lootbox");
        }

        Map<String, Integer> lootResults = new HashMap<>();

        for (int i = 0; i < pulls; i++) {
            Item obtainedItem = getRandomItem(items);
            lootResults.put(obtainedItem.getName(), lootResults.getOrDefault(obtainedItem.getName(), 0) + 1);
        }

        return lootResults;
    }
}
