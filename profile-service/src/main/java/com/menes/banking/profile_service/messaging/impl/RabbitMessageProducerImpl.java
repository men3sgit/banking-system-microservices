package com.menes.banking.profile_service.messaging.impl;

import com.menes.banking.profile_service.messaging.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMessageProducerImpl implements MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessageToQueue(Object payload, String destination) {
        rabbitTemplate.convertAndSend(destination, payload);
    }
}
