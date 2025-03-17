package com.studentgacha.lootbox.service.impl;

import com.studentgacha.lootbox.DTOs.LootboxOpenedEventDTO;
import com.studentgacha.lootbox.service.LootboxEventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LootboxEventPublisherImpl implements LootboxEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.lootbox.queue:lootbox.events}") // Default to "lootbox.events" if property is missing
    private String lootboxQueue;


    public LootboxEventPublisherImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void publishLootboxOpened(Long userId, Long itemId) {
        LootboxOpenedEventDTO event = new LootboxOpenedEventDTO(userId, itemId);
        rabbitTemplate.convertAndSend(lootboxQueue, event);
    }
}
