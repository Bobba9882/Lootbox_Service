package com.studentgacha.lootbox.Service.impl;

import com.studentgacha.lootbox.model.Item;
import com.studentgacha.lootbox.model.Lootbox;
import com.studentgacha.lootbox.repository.LootboxRepository;
import com.studentgacha.lootbox.service.LootboxEventPublisher;
import com.studentgacha.lootbox.service.impl.LootboxServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LootboxServiceImplTest {

    @Mock
    private LootboxRepository lootboxRepository;

    @Mock
    private LootboxEventPublisher eventPublisher;

    @InjectMocks
    private LootboxServiceImpl lootboxService;

    private Lootbox testLootbox;
    private Item commonItem;
    private Item rareItem;

    @BeforeEach
    void setUp() {
        // Setup test items
        commonItem = new Item("Common item", 50, 0.6,"A Common item description", testLootbox);
        rareItem = new Item("Rare item", 100, 0.2,"A Rare item description", testLootbox);

        // Setup lootbox
        testLootbox = new Lootbox("lootbox", 50);
        testLootbox.setItems(Arrays.asList(commonItem, rareItem));
    }

    @Test
    void openLootbox_shouldReturnItemAndPublishEvent() {
        // Arrange
        Long userId = 1L;
        Long lootboxId = 1L;

        when(lootboxRepository.findById(lootboxId)).thenReturn(Optional.of(testLootbox));

        // Act
        Item obtainedItem = lootboxService.openLootbox(userId, lootboxId);

        // Assert
        assertNotNull(obtainedItem);
        assertTrue(testLootbox.getItems().contains(obtainedItem));
        verify(eventPublisher, times(1)).publishLootboxOpened(userId, obtainedItem.getId());
    }

    @Test
    void openLootbox_shouldThrowException_whenLootboxNotFound() {
        // Arrange
        Long lootboxId = 1L;
        when(lootboxRepository.findById(lootboxId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> lootboxService.openLootbox(1L, lootboxId));
        assertEquals("Lootbox not found", exception.getMessage());
    }

    @Test
    void openLootbox_shouldThrowException_whenLootboxIsEmpty() {
        // Arrange
        Lootbox emptyLootbox = new Lootbox("Empty Lootbox", 50);
        emptyLootbox.setItems(List.of());

        when(lootboxRepository.findById(1L)).thenReturn(Optional.of(emptyLootbox));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> lootboxService.openLootbox(1L, 1L));
        assertEquals("No items found in this lootbox", exception.getMessage());
    }

    @Test
    void openLootboxMultipleTimes_shouldReturnCorrectItemCounts() {
        // Arrange
        when(lootboxRepository.findById(1L)).thenReturn(Optional.of(testLootbox));

        // Act
        Map<String, Integer> result = lootboxService.openLootboxMultipleTimes(1L, 1000);

        // Assert
        assertTrue(result.containsKey("Common item"));
        assertTrue(result.containsKey("Rare item"));
        assertTrue(result.get("Common item") > result.get("Rare item")); // Common items should appear more frequently
    }

    @Test
    void openLootboxMultipleTimes_shouldThrowException_whenLootboxNotFound() {
        // Arrange
        when(lootboxRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> lootboxService.openLootboxMultipleTimes(1L, 10));
        assertEquals("Lootbox not found", exception.getMessage());
    }

    @Test
    void openLootboxMultipleTimes_shouldThrowException_whenLootboxIsEmpty() {
        // Arrange
        Lootbox emptyLootbox = new Lootbox("Empty Lootbox", 50);
        emptyLootbox.setItems(List.of());

        when(lootboxRepository.findById(1L)).thenReturn(Optional.of(emptyLootbox));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> lootboxService.openLootboxMultipleTimes(1L, 5));
        assertEquals("No items found in this lootbox", exception.getMessage());
    }

}
