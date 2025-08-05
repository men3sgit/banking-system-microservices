package com.menes.banking.auth_service.messaging.consumer;

import com.menes.banking.auth_service.messaging.model.Event;
import com.menes.banking.auth_service.service.EventService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class EventConsumer <D, C> {

    protected final EventService eventService;

    protected abstract Object handleEvent(Event<D, C> event);

}
