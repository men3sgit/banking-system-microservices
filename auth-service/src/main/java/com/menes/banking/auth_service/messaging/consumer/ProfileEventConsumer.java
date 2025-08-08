package com.menes.banking.auth_service.messaging.consumer;

import org.springframework.stereotype.Component;

@Component("profile-event-consumer")
public class ProfileEventConsumer extends KafkaEventConsumer {

    protected String destinationQueue() {
//        return queueProperties.getProfileQueueName();
        return null;
    }
}