package com.menes.banking.profile_service.messaging.consumer.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.menes.banking.profile_service.exception.DomainCode;
import com.menes.banking.profile_service.exception.DomainException;
import com.menes.banking.profile_service.messaging.consumer.EventConsumer;
import com.menes.banking.profile_service.messaging.model.Event;
import com.menes.banking.profile_service.messaging.model.Event.EventType;
import com.menes.banking.profile_service.messaging.model.ProfileEvent;
import com.menes.banking.profile_service.repository.model.Profile;
import com.menes.banking.profile_service.service.EventService;
import com.menes.banking.profile_service.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProfileQueueConsumer extends EventConsumer<ProfileEvent> {
    private static final String PROCESS_NAME = "PROFILE_QUEUE_CONSUMER";

    private static final List<EventType> SUPPORTED_PROFILE_EVENT_TYPES = List.of(Event.EventType.PROFILE_CREATED, EventType.PROFILE_UPDATED);
    private final ProfileService profileService;

    public ProfileQueueConsumer(EventService eventService, ProfileService profileService) {
        super(eventService);
        this.profileService = profileService;
    }

    @RabbitListener(queues = "${rabbit.profile-event.queue}")
    public void consumeProfileEvent(@Payload String message) {
        log.info("[{}] Message received: {}", PROCESS_NAME, message);
        super.onReceiveEvent(message);
        log.info("[{}] Message consumed", PROCESS_NAME);
    }

    @Override
    protected JavaType getEventType() {
        return TypeFactory.defaultInstance()
                .constructParametricType(Event.class, Profile.class);
    }

    @Override
    protected boolean isEventExpected(Event<ProfileEvent> event) {
        return SUPPORTED_PROFILE_EVENT_TYPES.contains(event.getEventType());
    }

    @Override
    protected Object handleEvent(Event<ProfileEvent> event) {
        // 1) Validate input sớm, rõ ràng
        if (event == null || event.getEventType() == null || event.getData() == null) {
            log.warn("Invalid event payload: {}", event);
            throw new DomainException(DomainCode.EVENT_MISSING_FIELD);
        }

        // 2) (Tùy chọn) chống xử lý trùng theo eventId
        // if (idempotencyStore.alreadyProcessed(event.getId())) return null;

        // 3) Định tuyến theo event type
        try {
            switch (event.getEventType()) {
                case PROFILE_CREATED -> profileService.createProfile(event.getData());
                case PROFILE_UPDATED -> profileService.updateProfile(event.getData());
                default -> {
                    // Khi enum mở rộng mà chưa kịp xử lý: đẩy DLQ thay vì nuốt lỗi
                    log.error("Unsupported event type: {}", event.getEventType());
                    throw new DomainException(DomainCode.EVENT_UNSUPPORTED_TYPE);
                }
            }
        } catch (DomainException e) {
            log.error("Failed to handle event {}: {}", event.getId(), e.getMessage(), e);
            throw e; // để infra quyết định retry/DLQ
        }

        // idempotencyStore.markProcessed(event.getId());
        return null; // hoặc đổi sang void/ack boolean nếu có thể
    }

}
