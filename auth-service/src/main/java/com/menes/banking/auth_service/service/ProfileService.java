package com.menes.banking.auth_service.service;

import com.menes.banking.auth_service.messaging.model.Event;
import com.menes.banking.auth_service.messaging.model.ProfileEvent;
import com.menes.banking.auth_service.repository.model.Profile;

public interface ProfileService {

    boolean existsEmail(String email);

    void createProfile(Profile profile);

    void processProfileEvent(Event<ProfileEvent> event);

}
