package com.studentgacha.lootbox.Service.impl;

import com.studentgacha.lootbox.DTOs.LootboxOpenedEventDTO;
import com.studentgacha.lootbox.service.impl.LootboxEventPublisherImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LootboxEventPublisherImplTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private LootboxEventPublisherImpl lootboxEventPublisher;

    private final String queueName = "lootbox.events"; // Expected queue name

    @BeforeEach
    void setUp() {
        // Manually inject the queue name since @Value does not get injected in tests
        lootboxEventPublisher = new LootboxEventPublisherImpl(rabbitTemplate);

        // Use reflection to manually inject the queue name
        try {
            java.lang.reflect.Field field = LootboxEventPublisherImpl.class.getDeclaredField("lootboxQueue");
            field.setAccessible(true);
            field.set(lootboxEventPublisher, queueName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set lootboxQueue field", e);
        }
    }

    @Test
    void publishLootboxOpened_shouldSendEventToRabbitMQ() {
        // Arrange
        Long userId = 1L;
        Long itemId = 10L;
        LootboxOpenedEventDTO expectedEvent = new LootboxOpenedEventDTO(userId, itemId);

        // Act
        lootboxEventPublisher.publishLootboxOpened(userId, itemId);

        // Assert
        verify(rabbitTemplate, times(1)).convertAndSend(eq(queueName), any(LootboxOpenedEventDTO.class));
    }
}
