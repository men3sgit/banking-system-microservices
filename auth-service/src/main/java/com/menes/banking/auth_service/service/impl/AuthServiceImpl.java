package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.config.properties.InternalTopicProperties;
import com.menes.banking.auth_service.controller.model.*;
import com.menes.banking.auth_service.messaging.model.Event;
import com.menes.banking.auth_service.messaging.model.EventType;
import com.menes.banking.auth_service.messaging.model.MessageType;
import com.menes.banking.auth_service.messaging.model.ProfileEvent;
import com.menes.banking.auth_service.messaging.producer.KafkaProfileProducer;
import com.menes.banking.auth_service.repository.model.Profile;
import com.menes.banking.auth_service.service.AuthService;
import com.menes.banking.auth_service.service.ProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final ProfileService profileService;
//    private final ProfileClient profileClient; decoupling use EDA
    private final KafkaProfileProducer kafkaProfileProducer;
    private final InternalTopicProperties internalTopicProperties;
//    private final OtpService otpService;

    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(profileService.existsEmail(request.getEmail())) {
            log.info("Email already exists");
            throw new IllegalArgumentException("Email already exists");
        }
        Profile newProfile = Profile.builder()
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        profileService.createProfile(newProfile);

        ProfileEvent profileEvent = ProfileEvent.copyFrom(newProfile);

        Event<ProfileEvent> event = Event.<ProfileEvent>builder()
                .data(profileEvent)
                .messageType(MessageType.MESSAGE_OUT)
                .eventType(EventType.PROFILE_CREATED)
                .ackQueueType(Event.AckQueueType.KAFKA)
                .build();

        kafkaProfileProducer.publishEvent(event, internalTopicProperties.getProfileTopicName()); // TODO: improve after commit

        return null;
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        return null;
    }

    @Override
    public void logout(LogoutRequest request) {

    }

    @Override
    public void callProfileClient(String request) {
        log.info("Call profile client from auth service with request {}", request);
//        profileClient.verifyProfileExistsByEmail(request);
        log.info("Profile verified successfully");
    }
}
