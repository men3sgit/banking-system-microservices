package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.service.OtpService;
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
    public String generateOtp(String profileId, String type, Map<String, Object> attrs) {
        return "";
    }

    @Override
    public boolean validateOtp(String profileId, String type, String otp) {
        return false;
    }
}
