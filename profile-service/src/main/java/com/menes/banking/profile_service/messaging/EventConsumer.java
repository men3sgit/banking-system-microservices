package com.menes.banking.profile_service.messaging;

import com.fasterxml.jackson.databind.JavaType;
import com.menes.banking.profile_service.messaging.model.Event;
import com.menes.banking.profile_service.service.EventService;
import com.menes.banking.profile_service.utils.JsonHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class EventConsumer<D> {
    private static final String PROCESS_NAME = "EVENT_CONSUMER";

    private final EventService eventService;

    protected Event<D> parseMessage(String message) {
        return JsonHelper.getInstance().readValue(message, this.getEventType());
    }

    protected void onReceiveEvent(String message) {
        Event<D> event = this.parseMessage(message);
        final String traceId = event.getEventType() + ":" + event.getId();
        log.info("[{}][{}] Receive event: {}", PROCESS_NAME, traceId, event);
        try {
            log.info("[{}][{}] Start processing event", PROCESS_NAME, traceId);

            this.handleEvent(event);
            log.info("[{}][{}] Successfully processed event", PROCESS_NAME, traceId);
            even
        } catch (Exception e) {
            log.error("[{}][{}] Failed to process event: {}", PROCESS_NAME, traceId, e.getMessage(), e);
        }
    }

    protected abstract JavaType getEventType();

    protected abstract String getUnExpectedEventReason();

    protected abstract boolean isEventExpected(Event<D> event);

    protected abstract Object handleEvent(Event<D> event);
}
