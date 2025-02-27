package com.studentgacha.lootbox.service.impl;

import com.studentgacha.lootbox.DTOs.LootboxOpenedEvent;
import com.studentgacha.lootbox.service.LootboxEventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class LootboxEventPublisherImpl implements LootboxEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public LootboxEventPublisherImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void publishLootboxOpened(Long userId, Long itemId) {
        LootboxOpenedEvent event = new LootboxOpenedEvent(userId, itemId);
        String lootboxQueue = "${rabbitmq.lootbox.queue}";
        rabbitTemplate.convertAndSend(lootboxQueue, event);
        System.out.println("Published event: " + event);
    }
}
