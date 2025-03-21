package com.studentgacha.lootbox.controller;

import com.studentgacha.lootbox.model.Item;
import com.studentgacha.lootbox.repository.ItemRepository;
import com.studentgacha.lootbox.service.LootboxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LootboxControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LootboxService lootboxService;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private LootboxController lootboxController;

    private Item testItem;
    private final Long lootboxId = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(lootboxController).build();

        // Sample item setup
        testItem = new Item("Test Item", 100, 0.5, "Test Description", null);
        testItem.setId(1L);
    }

    @Test
    void openLootbox_shouldReturnItem() throws Exception {
        // Arrange
        when(lootboxService.openLootbox(any(Long.class), any(Long.class))).thenReturn(testItem);

        // Act & Assert
        mockMvc.perform(post("/api/lootboxes/{id}/open", lootboxId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testItem.getId()))
                .andExpect(jsonPath("$.name").value(testItem.getName()))
                .andExpect(jsonPath("$.itemValue").value(testItem.getItemValue()))
                .andExpect(jsonPath("$.dropChance").value(testItem.getDropChance()))
                .andExpect(jsonPath("$.description").value(testItem.getDescription()));
    }

    @Test
    void openMultipleLootboxes_shouldReturnCorrectResults() throws Exception {
        // Arrange
        Map<String, Integer> mockResults = Map.of("Test Item", 2, "Rare Item", 1);
        when(lootboxService.openLootboxMultipleTimes(any(Long.class), any(Integer.class))).thenReturn(mockResults);

        // Act & Assert
        mockMvc.perform(post("/api/lootboxes/{id}/open/{pulls}", lootboxId, 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Test Item']").value(2))
                .andExpect(jsonPath("$.['Rare Item']").value(1));
    }
}
