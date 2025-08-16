package com.menes.banking.profile_service.messaging.consumer.impl;

import com.menes.banking.profile_service.config.props.ProfileEventProps;
import com.menes.banking.profile_service.messaging.consumer.KafkaEventConsumer;
import com.menes.banking.profile_service.messaging.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("profile-events-channel")
@Slf4j
public class ProfileEventConsumer extends KafkaEventConsumer {

    private final MessageProducer messageProducer;

    private final ProfileEventProps  props;

    @Override
    protected void sendMessageToQueue(Object payload) {
        log.info("Sending event to queue: {}", payload);
        messageProducer.sendMessageToQueue(payload, props.getQueue());
        log.info("Sent event to queue: {} successfully", payload);

    }
}
