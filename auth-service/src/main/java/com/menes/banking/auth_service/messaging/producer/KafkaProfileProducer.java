package com.menes.banking.auth_service.messaging.producer;

import com.menes.banking.auth_service.messaging.model.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProfileProducer {

    private static final String OTP_NOTIFICATION_TOPIC = "otp-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;


    /**
     * Publish OTP notification event
     */
    public void publishEvent(Event<?> event, String topic) {
        try {
            kafkaTemplate.send(topic, event);
            log.info("✅ Published {} to topic={} with payload={}", event.getEventType(), topic, event);
        } catch (Exception ex) {
            log.error("❌ Failed to publish {}: {}", event.getEventType(), ex.getMessage(), ex);
        }
    }

}
