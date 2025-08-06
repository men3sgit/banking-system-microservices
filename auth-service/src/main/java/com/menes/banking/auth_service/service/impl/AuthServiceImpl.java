package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.connector.ProfileClient;
import com.menes.banking.auth_service.dto.*;
import com.menes.banking.auth_service.messaging.model.OtpNotificationEvent;
import com.menes.banking.auth_service.messaging.producer.KafkaProfileProducer;
import com.menes.banking.auth_service.repository.model.Profile;
import com.menes.banking.auth_service.repository.ProfileRepository;
import com.menes.banking.auth_service.service.AuthService;
import com.menes.banking.auth_service.service.OtpService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final ProfileRepository profileRepository;
    private final ProfileClient profileClient;
    private final KafkaProfileProducer kafkaProfileProducer;
    private final OtpService otpService;
    private final  ExecutorService executorService;

    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (profileRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        var newProfile = Profile.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .build();
        profileRepository.save(newProfile);

        var otp = otpService.generateOtp(newProfile.getId());

        var otpEvent = OtpNotificationEvent.builder()
                .phoneNumber(request.getPhoneNumber())
                .channels(List.of("SMS"))
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .otp(otp)
                .build();

        executorService.submit(() -> kafkaProfileProducer.publishOtpNotification(otpEvent));

        return new RegisterResponse("User registered successfully");
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
