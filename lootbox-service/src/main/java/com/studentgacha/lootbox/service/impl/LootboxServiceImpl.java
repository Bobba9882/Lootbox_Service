package com.studentgacha.lootbox.service.impl;

import com.studentgacha.lootbox.model.Item;
import com.studentgacha.lootbox.model.Lootbox;
import com.studentgacha.lootbox.repository.ItemRepository;
import com.studentgacha.lootbox.repository.LootboxRepository;
import com.studentgacha.lootbox.service.LootboxEventPublisher;
import com.studentgacha.lootbox.service.LootboxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class LootboxServiceImpl implements LootboxService {

    private final LootboxEventPublisher eventPublisher;
    private final LootboxRepository lootboxRepository;
    private final ItemRepository itemRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public LootboxServiceImpl(
            final LootboxRepository lootboxRepository,
            final ItemRepository itemRepository,
            final LootboxEventPublisher eventPublisher) {
        this.lootboxRepository = lootboxRepository;
        this.itemRepository = itemRepository;
        this.eventPublisher = eventPublisher;
    }

    // Lootbox Management

    @Override
    public Lootbox createLootbox(final Lootbox lootbox) {
        return lootboxRepository.save(lootbox);
    }

    @Override
    public Lootbox updateLootbox(final Long lootboxId, final Lootbox updatedLootbox) {
        Lootbox lootbox = findLootboxById(lootboxId);
        lootbox.setName(updatedLootbox.getName());
        lootbox.setPrice(updatedLootbox.getPrice());
        return lootboxRepository.save(lootbox);
    }

    @Transactional
    @Override
    public void deleteLootbox(final Long lootboxId) {
        Lootbox lootbox = findLootboxById(lootboxId);
        itemRepository.deleteAll(lootbox.getItems());
        lootboxRepository.delete(lootbox);
    }

    @Override
    public List<Lootbox> getAllLootboxes() {
        return lootboxRepository.findAll();
    }

    // Item Management

    @Override
    public Item addItemToLootbox(final Long lootboxId, final Item item) {
        Lootbox lootbox = findLootboxById(lootboxId);
        item.setLootbox(lootbox);
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getLootboxContents(final Long lootboxId) {
        return findLootboxById(lootboxId).getItems();
    }

    @Override
    public Item updateItem(final Long itemId, final Item updatedItem) {
        Item item = findItemById(itemId);
        item.setName(updatedItem.getName());
        item.setItemValue(updatedItem.getItemValue());
        item.setDropChance(updatedItem.getDropChance());
        item.setDescription(updatedItem.getDescription());
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(final Long itemId) {
        Item item = findItemById(itemId);
        itemRepository.delete(item);
    }

    // Lootbox Opening Mechanics

    @Override
    public Item openLootbox(final Long userId, final Long lootboxId) {
        Lootbox lootbox = findLootboxById(lootboxId);
        List<Item> items = lootbox.getItems();

        if (items.isEmpty()) {
            throw new RuntimeException("Lootbox is empty");
        }

        Item obtainedItem = getRandomItem(items);
        eventPublisher.publishLootboxOpened(userId, obtainedItem.getId());

        return obtainedItem;
    }

    @Override
    public Map<String, Integer> openLootboxMultipleTimes(final Long lootboxId, final int pulls) {
        Lootbox lootbox = findLootboxById(lootboxId);
        List<Item> items = lootbox.getItems();

        if (items.isEmpty()) {
            throw new RuntimeException("Lootbox is empty");
        }

        Map<String, Integer> lootResults = new HashMap<>();
        for (int i = 0; i < pulls; i++) {
            Item obtainedItem = getRandomItem(items);
            lootResults.put(obtainedItem.getName(), lootResults.getOrDefault(obtainedItem.getName(), 0) + 1);
        }

        return lootResults;
    }

    // Helper Methods

    private Lootbox findLootboxById(final Long lootboxId) {
        return lootboxRepository.findById(lootboxId)
                .orElseThrow(() -> new RuntimeException("Lootbox not found"));
    }

    private Item findItemById(final Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    private Item getRandomItem(final List<Item> items) {
        double totalWeight = items.stream().mapToDouble(Item::getDropChance).sum();
        double randomValue = ThreadLocalRandom.current().nextDouble() * totalWeight;
        randomValue += secureRandom.nextDouble() * 0.00001;  // Small entropy boost for randomness

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
