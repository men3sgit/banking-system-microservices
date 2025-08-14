package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.config.properties.InternalTopicProperties;
import com.menes.banking.auth_service.controller.model.*;
import com.menes.banking.auth_service.exception.DomainCode;
import com.menes.banking.auth_service.exception.DomainException;
import com.menes.banking.auth_service.messaging.model.Event;
import com.menes.banking.auth_service.messaging.model.Event.MessageType;
import com.menes.banking.auth_service.messaging.model.EventType;
import com.menes.banking.auth_service.messaging.model.OtpEvent;
import com.menes.banking.auth_service.messaging.model.ProfileEvent;
import com.menes.banking.auth_service.messaging.producer.KafkaProducer;
import com.menes.banking.auth_service.repository.model.Profile;
import com.menes.banking.auth_service.service.AuthService;
import com.menes.banking.auth_service.service.OtpService;
import com.menes.banking.auth_service.service.ProfileService;
import com.menes.banking.auth_service.service.model.OtpResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final ProfileService profileService;
    //    private final ProfileClient profileClient; decoupling use EDA
    private final KafkaProducer kafkaProducer;
    private final InternalTopicProperties internalTopicProperties;
    private final OtpService otpService;

    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest request) {
        final String email = request.getEmail().trim().toLowerCase(Locale.ROOT);

        if (profileService.existsEmail(email)) {
            throw new DomainException(DomainCode.EMAIL_EXISTS);
        }

        Profile newProfile = Profile.builder()
                .email(email)
                .phoneNumber(request.getPhoneNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        Profile saved = profileService.createProfile(newProfile);

        publishProfileCreatedEvent(saved);

        publishOtpEvent(saved);

        return new RegisterResponse("Create new profile successfully", saved);
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

    private void publishProfileCreatedEvent(Profile profile) {
        ProfileEvent profileEventData = ProfileEvent.copyFrom(profile);

        Event<ProfileEvent> event = Event.<ProfileEvent>builder()
                .data(profileEventData)
                .messageType(Event.MessageType.MESSAGE_OUT)
                .eventType(EventType.PROFILE_CREATED)
                .ackQueueType(Event.AckQueueType.KAFKA)
                .build();

        kafkaProducer.publishEvent(event, internalTopicProperties.getProfileTopicName());
    }

    private void publishOtpEvent(Profile profile) {
        OtpResult otpResult = otpService.generateOtp();

        OtpEvent otpEvent = OtpEvent.builder()
                .channel("SMS")
                .code(otpResult.getCode())
                .destination(profile.getPhoneNumber())
                .build();

        Event<OtpEvent> event = Event.<OtpEvent>builder()
                .data(otpEvent)
                .messageType(MessageType.MESSAGE_OUT)
                .eventType(EventType.PROFILE_CREATED) // hoặc OTP_CREATED nếu sau này tách riêng
                .ackQueueType(Event.AckQueueType.KAFKA)
                .build();

        kafkaProducer.publishEvent(event, internalTopicProperties.getNotificationTopicName());
    }

}
