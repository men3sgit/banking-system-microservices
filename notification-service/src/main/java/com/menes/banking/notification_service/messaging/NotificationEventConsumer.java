package com.menes.banking.notification_service.messaging;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.menes.banking.notification_service.messaging.model.Event;
import com.menes.banking.notification_service.messaging.model.Event.EventType;

import com.menes.banking.notification_service.messaging.model.NotificationEvent;
import com.menes.banking.notification_service.messaging.model.OtpEvent;
import com.menes.banking.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class NotificationEventConsumer extends EventConsumer<OtpEvent> {

    private static final List<EventType> SUPPORTED_OTP_EVENT_TYPES =
            List.of(
                    EventType.OTP_REGISTER,
                    EventType.OTP_LOGIN,
                    EventType.OTP_RESET_PASSWORD,
                    EventType.OTP_TRANSACTION,
                    EventType.OTP_CHANGE_PHONE,
                    EventType.OTP_CHANGE_EMAIL
            );

    private final NotificationService notificationService;


    @KafkaListener(topics = "notification-events-topic", groupId = "notification-consumer-group")
    public void consume(String message) {
        System.out.println("📥 [Kafka] Received raw message: " + message);

        Event<OtpEvent> event = parseMessage(message);
        System.out.println("✅ [Kafka] Parsed Event: " + event);

//        if (!isEventExpected(event)) {
//            System.err.println("❌ [Kafka] Invalid OTP Event: " + getUnExpectedEventReason());
//            return;
//        }
//
//        System.out.printf("🔎 [Kafka] Processing OTP Event Type: %s for User: %s%n",
//                event.getEventType(), event.getData().getProfileId());
//
        handleEvent(event);

        System.out.println("🎉 [Kafka] Successfully handled OTP event for user: ");
//                + event.getData().getProfileId());
    }


    @Override
    protected JavaType getEventType() {
        return TypeFactory.defaultInstance()
                .constructParametricType(Event.class, NotificationEvent.class);
    }

    @Override
    protected boolean isEventExpected(Event<OtpEvent> event) {
        return SUPPORTED_OTP_EVENT_TYPES.contains(event.getEventType());
    }


    @Override
    protected Object handleEvent(Event<OtpEvent> event) {
        if (!isEventExpected(event)) {
            return getUnExpectedEventReason();
        }
        var data = event.getData();




        return null;
    }

    @Override
    protected String getUnExpectedEventReason() {
        return "Missing or invalid event type/data";
    }
}
