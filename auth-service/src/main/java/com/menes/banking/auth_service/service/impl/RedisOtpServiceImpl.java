package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.controller.model.OtpRequest;
import com.menes.banking.auth_service.service.OtpService;
import com.menes.banking.auth_service.service.model.OtpResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisOtpServiceImpl implements OtpService {

    private static final int OTP_LENGTH = 6;
    private static final Duration OTP_EXPIRATION = Duration.ofMinutes(5);

    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public OtpResult generateOtp() {
        return null;
    }

    @Override
    public boolean validate(OtpRequest request) {
        return false;
    }

    @Override
    public OtpResult resend(String otpId) {
        return null;
    }
}
