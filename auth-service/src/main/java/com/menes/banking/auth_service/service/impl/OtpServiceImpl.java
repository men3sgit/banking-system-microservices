package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.service.OtpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Primary
@Service
@Slf4j
public class OtpServiceImpl implements OtpService {

    /**
     * Improvements to consider in the future:
     *
     * 1. **Distributed Cache / Persistent Storage:**
     *    - Currently using an in-memory Map (not suitable for production).
     *    - Migrate to Redis with TTL for OTP expiration and horizontal scalability.
     *
     * 2. **Delivery Channels:**
     *    - Integrate with SMS/email gateways (e.g., Twilio, AWS SNS/SES) to deliver OTPs.
     *    - Support multi-channel OTP delivery and fallback mechanisms.
     *
     * 3. **Security Enhancements:**
     *    - Add rate limiting to prevent OTP spam requests.
     *    - Track and limit invalid OTP attempts (e.g., lock user after 5 failed attempts).
     *    - Encrypt OTP values at rest if stored persistently.
     *
     * 4. **Audit & Monitoring:**
     *    - Log OTP generation and validation events for security auditing.
     *    - Add metrics (success/failure rates, OTP request volume) for monitoring with Prometheus/Grafana.
     *
     * 5. **Advanced OTP Mechanisms:**
     *    - Implement TOTP (Time-based OTP) using RFC 6238 for stateless validation.
     *    - Support alphanumeric or longer OTPs for better security.
     *
     * 6. **Extensibility:**
     *    - Allow OTP usage in multiple contexts (registration, password reset, transaction verification).
     *    - Parameterize expiration duration per use case.
     *
     * 7. **Cloud Services Integration:**
     *    - Offload OTP generation/delivery to managed identity services (AWS Cognito, Firebase Auth, Twilio Verify).
     */

    private static final int OTP_LENGTH = 6;
    private static final Duration OTP_EXPIRATION = Duration.ofMinutes(5);

    // Temporary in-memory storage: userId -> OtpInfo
    private final Map<String, OtpInfo> otpStore = new HashMap<>();

    @PostConstruct
    public void initDefaultOtps() {
        otpStore.put("user-111", new OtpInfo("111111", OTP_EXPIRATION));
        otpStore.put("user-222", new OtpInfo("222222", OTP_EXPIRATION));
        otpStore.put("user-333", new OtpInfo("333333", OTP_EXPIRATION));
        log.info("Initialized default OTPs: [111111, 222222, 333333]");
    }

    @Override
    public String generateOtp(String userId) {
        String otp = String.format("%0" + OTP_LENGTH + "d", new SecureRandom().nextInt((int) Math.pow(10, OTP_LENGTH)));
        otpStore.put(userId, new OtpInfo(otp, OTP_EXPIRATION));
        log.info("Generated OTP for userId={} otp={}", userId, otp);
        return otp;
    }

    @Override
    public boolean validateOtp(String userId, String otp) {
        Optional<OtpInfo> otpInfoOpt = Optional.ofNullable(otpStore.get(userId));

        if (otpInfoOpt.isEmpty()) {
            log.warn("OTP validation failed: No OTP found for userId={}", userId);
            return false;
        }

        OtpInfo otpInfo = otpInfoOpt.get();

        if (otpInfo.isExpired()) {
            log.warn("OTP validation failed: OTP expired for userId={}", userId);
            otpStore.remove(userId);
            return false;
        }

        if (otpInfo.getOtp().equals(otp)) {
            otpStore.remove(userId);
            log.info("OTP validated successfully for userId={}", userId);
            return true;
        }

        log.warn("OTP validation failed: Incorrect OTP for userId={}", userId);
        return false;
    }

    private static class OtpInfo {
        private final String otp;
        private final Duration expiryDuration;
        private final LocalDateTime createdAt;

        public OtpInfo(String otp, Duration expiryDuration) {
            this.otp = otp;
            this.expiryDuration = expiryDuration;
            this.createdAt = LocalDateTime.now();
        }

        public String getOtp() {
            return otp;
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(createdAt.plus(expiryDuration));
        }
    }
}
