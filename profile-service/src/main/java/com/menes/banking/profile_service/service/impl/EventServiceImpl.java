package com.menes.banking.profile_service.service.impl;

import com.menes.banking.profile_service.messaging.model.Event;
import com.menes.banking.profile_service.service.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
    private static final String PROCESS_NAME = "Event Service";
//    private EventAuditRepository eventAuditRepository;
    @Override
    public void markEventAsConsumed(Event event) {

    }

    @Override
    public void markEventAsPending(Event event) {

    }
}
