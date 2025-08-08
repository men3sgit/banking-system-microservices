package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.connector.ProfileClient;
import com.menes.banking.auth_service.controller.model.*;
import com.menes.banking.auth_service.messaging.model.Event;
import com.menes.banking.auth_service.messaging.model.EventType;
import com.menes.banking.auth_service.messaging.model.OtpNotificationEvent;
import com.menes.banking.auth_service.messaging.producer.KafkaProfileProducer;
import com.menes.banking.auth_service.repository.model.Profile;
import com.menes.banking.auth_service.repository.ProfileRepository;
import com.menes.banking.auth_service.service.AuthService;
import com.menes.banking.auth_service.service.OtpService;
import com.menes.banking.auth_service.service.model.DeliveryChannel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final ProfileRepository profileRepository;
    private final ProfileClient profileClient;
    private final KafkaProfileProducer kafkaProfileProducer;
    private final OtpService otpService;
    private final ExecutorService executorService;

    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest request) {



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
        profileClient.verifyProfileExistsByEmail(request);
        log.info("Profile verified successfully");
    }
}
