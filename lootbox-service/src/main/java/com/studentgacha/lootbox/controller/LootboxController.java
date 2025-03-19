package com.studentgacha.lootbox.controller;

import com.studentgacha.lootbox.DTOs.ItemDTO;
import com.studentgacha.lootbox.model.Item;
import com.studentgacha.lootbox.model.Lootbox;
import com.studentgacha.lootbox.service.LootboxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lootboxes")
@Tag(name = "Lootbox API", description = "Manage and open lootboxes")
public class LootboxController {

    private final LootboxService lootboxService;

    public LootboxController(LootboxService lootboxService) {
        this.lootboxService = lootboxService;
    }

    // Lootbox Management

    @Operation(summary = "Create a new lootbox", description = "Adds a new lootbox to the system.")
    @PostMapping
    public ResponseEntity<Lootbox> createLootbox(@Valid @RequestBody Lootbox lootbox) {
        Lootbox createdLootbox = lootboxService.createLootbox(lootbox);
        return ResponseEntity.ok(createdLootbox);
    }

    @Operation(summary = "Get all lootboxes", description = "Retrieves a list of all available lootboxes.")
    @GetMapping
    public ResponseEntity<List<Lootbox>> getAllLootboxes() {
        List<Lootbox> lootboxes = lootboxService.getAllLootboxes();
        return ResponseEntity.ok(lootboxes);
    }

    @Operation(summary = "Update lootbox details", description = "Modifies a lootbox's name, price, or other attributes.")
    @PutMapping("/{lootboxId}")
    public ResponseEntity<Lootbox> updateLootbox(
            @Parameter(description = "ID of the lootbox") @PathVariable Long lootboxId,
            @Valid @RequestBody Lootbox updatedLootbox) {
        Lootbox lootbox = lootboxService.updateLootbox(lootboxId, updatedLootbox);
        return ResponseEntity.ok(lootbox);
    }

    @Operation(summary = "Delete a lootbox", description = "Removes a lootbox and all associated items.")
    @DeleteMapping("/{lootboxId}")
    public ResponseEntity<String> deleteLootbox(
            @Parameter(description = "ID of the lootbox") @PathVariable Long lootboxId) {
        lootboxService.deleteLootbox(lootboxId);
        return ResponseEntity.ok("Lootbox and all associated items deleted successfully.");
    }

    // Item Management

    @Operation(summary = "Add an item to a lootbox", description = "Adds a new item to an existing lootbox.")
    @PostMapping("/{lootboxId}/items")
    public ResponseEntity<Item> addItemToLootbox(
            @Parameter(description = "ID of the lootbox") @PathVariable Long lootboxId,
            @Valid @RequestBody Item item) {
        Item savedItem = lootboxService.addItemToLootbox(lootboxId, item);
        return ResponseEntity.ok(savedItem);
    }

    @Operation(summary = "Get lootbox contents", description = "Retrieves all items inside a specific lootbox, including drop rates.")
    @GetMapping("/{lootboxId}/items")
    public ResponseEntity<List<ItemDTO>> getLootboxContents(
            @Parameter(description = "ID of the lootbox") @PathVariable Long lootboxId) {
        List<ItemDTO> items = lootboxService.getLootboxContents(lootboxId)
                .stream()
                .map(item -> new ItemDTO(
                        item.getId(),
                        item.getName(),
                        item.getItemValue(),
                        item.getDropChance(),
                        item.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Update item details", description = "Modifies an item's name, value, drop chance, or description.")
    @PutMapping("/items/{itemId}")
    public ResponseEntity<Item> updateItem(
            @Parameter(description = "ID of the item") @PathVariable Long itemId,
            @Valid @RequestBody Item updatedItem) {
        Item item = lootboxService.updateItem(itemId, updatedItem);
        return ResponseEntity.ok(item);
    }

    @Operation(summary = "Delete an item", description = "Removes an item from the database.")
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<String> deleteItem(
            @Parameter(description = "ID of the item") @PathVariable Long itemId) {
        lootboxService.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted successfully.");
    }

    // Lootbox Opening

    @Operation(summary = "Open a lootbox", description = "Opens a lootbox and returns the obtained item.")
    @PostMapping("/{lootboxId}/open")
    public ResponseEntity<Item> openLootbox(
            @Parameter(description = "ID of the lootbox") @PathVariable Long lootboxId) {
        Item obtainedItem = lootboxService.openLootbox(1L, lootboxId);
        return ResponseEntity.ok(obtainedItem);
    }

    @Operation(summary = "Open multiple lootboxes", description = "Opens a lootbox multiple times and returns a count of obtained items.")
    @PostMapping("/{lootboxId}/open/{pulls}")
    public ResponseEntity<Map<String, Integer>> openMultipleLootboxes(
            @Parameter(description = "ID of the lootbox") @PathVariable Long lootboxId,
            @Parameter(description = "Number of times to open the lootbox") @PathVariable int pulls) {
        Map<String, Integer> results = lootboxService.openLootboxMultipleTimes(lootboxId, pulls);
        return ResponseEntity.ok(results);
    }
}
