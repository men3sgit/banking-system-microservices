package com.menes.banking.notification_service.service.impl;

import com.menes.banking.notification_service.service.ContactService;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Override
    public String getPhoneNumber(String profileId) {
        return "profile-123";
    }
}
