package com.studentgacha.lootbox.service.impl;

import com.studentgacha.lootbox.DTOs.LootboxOpenedEvent;
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
        LootboxOpenedEvent event = new LootboxOpenedEvent(userId, itemId);
        System.out.println("Sending event to RabbitMQ: " + event);
        rabbitTemplate.convertAndSend(lootboxQueue, event);
        System.out.println("Event sent successfully!");
    }
}
