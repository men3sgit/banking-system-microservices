package com.menes.banking.profile_service.messaging.producer.impl;

import com.menes.banking.profile_service.messaging.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class RabbitMessageProducerImpl implements MessageProducer {
    private static final String PROCESS_NAME = "RABBIT_MESSAGE_PRODUCER";
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessageToQueue(Object payload, String destination) {
        log.info("[{}] Sending event to queue: {}", PROCESS_NAME, payload);
        rabbitTemplate.convertAndSend(destination, payload);
    }
}
