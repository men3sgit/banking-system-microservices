package com.menes.banking.notification_service.messaging;

import com.fasterxml.jackson.databind.JavaType;
import com.menes.banking.notification_service.messaging.model.Event;
import com.menes.banking.notification_service.utils.JsonHelper;

public abstract class EventConsumer<D> {

    protected Event<D> parseMessage(String message) {
        return JsonHelper.getInstance().readValue(message, getEventType());
    }

    protected abstract String getUnExpectedEventReason();

    protected abstract JavaType getEventType();

    protected abstract boolean isEventExpected(Event<D> event);

    protected abstract Object handleEvent(Event<D> event);
}
