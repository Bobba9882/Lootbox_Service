package com.studentgacha.lootbox.controller;

import com.studentgacha.lootbox.model.Item;
import com.studentgacha.lootbox.service.LootboxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/lootboxes")
public class LootboxController {
    private final LootboxService lootboxService;

    public LootboxController(LootboxService lootboxService) {
        this.lootboxService = lootboxService;
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
}
