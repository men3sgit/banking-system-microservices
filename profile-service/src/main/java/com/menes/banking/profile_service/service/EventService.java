package com.menes.banking.profile_service.service;

import com.menes.banking.profile_service.messaging.model.Event;

public interface EventService {

    void markEventAsConsumed(Event event);

    void markEventAsPending(Event event);

}
