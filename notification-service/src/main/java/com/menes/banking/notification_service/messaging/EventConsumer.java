package com.menes.banking.notification_service.messaging;

import com.fasterxml.jackson.databind.JavaType;
import com.menes.banking.notification_service.messaging.model.Event;
import com.menes.banking.notification_service.utils.JsonHelper;

import java.util.LinkedList;

public abstract class EventConsumer<D> {

    protected Event<D> parseMessage(String message) {
        return JsonHelper.getInstance().readValue(message, this.getEventType());
    }

    protected abstract JavaType getEventType();

    protected abstract String getUnExpectedEventReason();

    protected abstract boolean isEventExpected(Event<D> event);

    protected abstract Object handleEvent(Event<D> event);
}
