package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.messaging.model.Event;
import com.menes.banking.auth_service.messaging.model.EventType;
import com.menes.banking.auth_service.messaging.model.ProfileEvent;
import com.menes.banking.auth_service.repository.ProfileRepository;
import com.menes.banking.auth_service.repository.model.Profile;
import com.menes.banking.auth_service.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private static final List<EventType> SUPPORTED_EVENT_TYPES =
            List.of(EventType.PROFILE_CREATED, EventType.PROFILE_UPDATED);

    private final ProfileRepository profileRepository;

    @Override
    public boolean existsEmail(String email) {
        log.info("Checking if exists email {}", email);
        return profileRepository.existsByEmail(email);
    }

    @Override
    public Profile createProfile(Profile profile) {
        log.info("Creating profile {}", profile);
        profile.setStatus("WAITING_FOR_APPROVAL");
        return profileRepository.save(profile);
    }

    @Override
    public void processProfileEvent(Event<ProfileEvent> event) {
        // TODO: implement later
    }


    private boolean isSupportedEventType(Event<ProfileEvent> event) {
        return SUPPORTED_EVENT_TYPES.contains(event.getEventType());
    }
}
