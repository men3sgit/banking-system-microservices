package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.repository.EventAuditRepository;
import com.menes.banking.auth_service.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

//    private final EventAuditRepository eventAuditRepository;

    private static final String PROCESS_NAME = "Event Service";

}
