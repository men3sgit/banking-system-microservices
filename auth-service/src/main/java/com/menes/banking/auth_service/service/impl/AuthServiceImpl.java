package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.dto.*;
import com.menes.banking.auth_service.repository.model.Profile;
import com.menes.banking.auth_service.repository.UserRepository;
import com.menes.banking.auth_service.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        Profile newProfile = Profile.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .build();

        userRepository.save(newProfile);

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
}
