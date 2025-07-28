package com.menes.banking.auth_service.messaging.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProfileProducer {

    private static final String PROFILE_TOPIC = "user.profile";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishProfileEvent(Object profileEvent) {
        try {
            kafkaTemplate.send(PROFILE_TOPIC, profileEvent);
            log.info("Published Profile Event to topic={} with payload={}", PROFILE_TOPIC, profileEvent);
        } catch (Exception ex) {
            log.error("Failed to publish Profile Event: {}", ex.getMessage(), ex);
        }
    }
}
