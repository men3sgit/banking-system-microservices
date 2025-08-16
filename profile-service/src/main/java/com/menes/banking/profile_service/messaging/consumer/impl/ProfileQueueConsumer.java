package com.menes.banking.profile_service.messaging.consumer.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
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
    protected String getUnExpectedEventReason() {
        return "";
    }

    @Override
    protected boolean isEventExpected(Event<ProfileEvent> event) {
        return SUPPORTED_PROFILE_EVENT_TYPES.contains(event.getEventType());
    }

    @Override
    protected Object handleEvent(Event<ProfileEvent> event) {
        if (!isEventExpected(event)) {
            log.warn("Ignored - message type {} is not supported", event.getEventType());
            return null;
        }


        return null;
    }
}
