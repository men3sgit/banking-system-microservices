package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.service.OtpService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisOtpServiceImpl implements OtpService {

    private static final int OTP_LENGTH = 6;
    private static final Duration OTP_EXPIRATION = Duration.ofMinutes(5);

    private final RedisTemplate<String, String> redisTemplate;

    private String buildOtpKey(String userId) {
        return "OTP:" + userId;
    }

    @Override
    public String generateOtp(String userId) {
        String otp = String.format("%0" + OTP_LENGTH + "d", new SecureRandom().nextInt((int) Math.pow(10, OTP_LENGTH)));
        String key = buildOtpKey(userId);

        redisTemplate.opsForValue().set(key, otp, OTP_EXPIRATION.toMinutes(), TimeUnit.MINUTES);
        log.info("Generated OTP for userId={} otp={} (stored in Redis)", userId, otp);

        return otp;
    }

    @Override
    public boolean validateOtp(String userId, String otp) {
        String key = buildOtpKey(userId);
        String storedOtp = redisTemplate.opsForValue().get(key);

        if (storedOtp == null) {
            log.warn("OTP validation failed: No OTP found in Redis for userId={}", userId);
            return false;
        }

        if (storedOtp.equals(otp)) {
            redisTemplate.delete(key); // Remove after successful validation
            log.info("OTP validated successfully for userId={} (Redis)", userId);
            return true;
        }

        log.warn("OTP validation failed: Incorrect OTP for userId={}", userId);
        return false;
    }
}
