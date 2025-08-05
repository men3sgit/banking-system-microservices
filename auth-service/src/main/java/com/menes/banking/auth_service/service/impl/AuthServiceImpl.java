package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.connector.ProfileClient;
import com.menes.banking.auth_service.dto.*;
import com.menes.banking.auth_service.repository.model.Profile;
import com.menes.banking.auth_service.repository.ProfileRepository;
import com.menes.banking.auth_service.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final ProfileRepository profileRepository;
//    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProfileClient profileClient;

    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (profileRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        Profile newProfile = Profile.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .build();
//        kafkaTemplate.send("profile", newProfile.toString());
        profileRepository.save(newProfile);

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
