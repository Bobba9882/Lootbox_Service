package com.studentgacha.lootbox.controller;

import com.studentgacha.lootbox.DTOs.ItemDTO;
import com.studentgacha.lootbox.model.Item;
import com.studentgacha.lootbox.repository.ItemRepository;
import com.studentgacha.lootbox.service.LootboxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lootboxes")
public class LootboxController {
    private final LootboxService lootboxService;
    private final ItemRepository itemRepository;

    public LootboxController(LootboxService lootboxService, ItemRepository itemRepository) {
        this.lootboxService = lootboxService;
        this.itemRepository = itemRepository;
    }

    @PostMapping("/{id}/open")
    public ResponseEntity<Item> openLootbox(@PathVariable Long id) {
        Item item = lootboxService.openLootbox(1L, id);
        return ResponseEntity.ok(item);
    }

    @PostMapping("/{id}/open/{pulls}")
    public ResponseEntity<Map<String, Integer>> openMultipleLootboxes(@PathVariable Long id, @PathVariable int pulls) {
        Map<String, Integer> results = lootboxService.openLootboxMultipleTimes(id, pulls);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemDTO>> getItemsByIds(@RequestParam List<Long> ids) {
        List<Item> items = itemRepository.findAllById(ids);

        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ItemDTO> itemDTOs = items.stream()
                .map(item -> new ItemDTO(
                        item.getId(),
                        item.getName(),
                        item.getValue(),
                        item.getDropChance(),
                        item.getDescription()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(itemDTOs);
    }
}
