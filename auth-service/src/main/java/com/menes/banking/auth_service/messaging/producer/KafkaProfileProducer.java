package com.menes.banking.auth_service.messaging.producer;

import com.menes.banking.auth_service.messaging.model.EventType;
import com.menes.banking.auth_service.messaging.model.OtpNotificationEvent;
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
    public void publishOtpNotification(OtpNotificationEvent otpEvent) {
        sendMessage(OTP_NOTIFICATION_TOPIC, otpEvent, EventType.OTP_NOTIFICATION);
    }

    /**
     * Generic send logic
     */
    private void sendMessage(String topic, Object payload, EventType eventType) {
        try {
            kafkaTemplate.send(topic, payload);
            log.info("✅ Published {} to topic={} with payload={}", eventType, topic, payload);
        } catch (Exception ex) {
            log.error("❌ Failed to publish {}: {}", eventType, ex.getMessage(), ex);
        }
    }
}
