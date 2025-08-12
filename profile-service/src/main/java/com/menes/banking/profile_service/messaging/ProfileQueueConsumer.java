package com.menes.banking.profile_service.messaging;

import com.fasterxml.jackson.databind.JavaType;
import com.menes.banking.profile_service.messaging.model.Event;
import com.menes.banking.profile_service.messaging.model.Event.EventType;
import com.menes.banking.profile_service.messaging.model.ProfileEvent;
import com.menes.banking.profile_service.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileQueueConsumer extends EventConsumer<ProfileEvent> {

    private static final List<EventType> SUPPORTED_PROFILE_EVENT_TYPES = List.of(Event.EventType.PROFILE_CREATED, EventType.PROFILE_UPDATED);

    private final ProfileService profileService;

    @Override
    protected JavaType getEventType() {
        return null;
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
        ProfileEvent profileEvent = event.getData();
        profileService.createProfile(profileEvent);
        return null;
    }
}
