package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.service.OtpService;
import com.menes.banking.auth_service.service.model.OtpResult;
import lombok.Getter;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Service
public class BasicOtpServiceImpl implements OtpService {

    private static final int OTP_LENGTH = 6;
    private static final int TTL_SECONDS = 300;       // 5 phút
    private static final int MAX_ATTEMPTS = 5;

    private final SecureRandom random = new SecureRandom();

    // subject -> entry
    private final Map<String, Entry> store = new ConcurrentHashMap<>();

    @Override
    public OtpResult generateOtp(String subject) {
        String code = generateCode(OTP_LENGTH);
        String otpId = UUID.randomUUID().toString();
        Instant exp = Instant.now().plusSeconds(TTL_SECONDS);

        store.put(subject, new Entry(otpId, code, exp, 0)); // enhance later because override otp with the same subject

        return OtpResult.builder()
                .otpId(otpId)
                .code(code)
                .expiresAt(exp)
                .build();
    }


    public boolean validate(String subject, String code) {
        Entry e = store.get(subject);
        if (e == null) return false;
        if (Instant.now().isAfter(e.expiresAt)) {
            store.remove(subject);
            return false;
        }
        if (e.attempts >= MAX_ATTEMPTS) {
            store.remove(subject);
            return false;
        }
        e.attempts++;
        boolean ok = e.code.equals(code);
        if (ok) store.remove(subject);
        return ok;
    }


    private String generateCode(int len) {
        int bound = (int) Math.pow(10, len);
        int min = bound / 10;
        int n = random.nextInt(bound - min) + min; // đảm bảo đủ len chữ số, không bắt đầu bằng 0
        return String.valueOf(n);
    }

    // Dọn rác định kỳ (tuỳ chọn)
    @Scheduled(fixedDelay = 60_000)
    public void evictExpired() {
        Instant now = Instant.now();
        store.entrySet().removeIf(e -> now.isAfter(e.getValue().expiresAt));
    }

    @Getter
    private static final class Entry {
        final String code;
        final Instant expiresAt;
        int attempts;
        final String otpId;
        Entry(String otpId, String code, Instant expiresAt, int attempts) {
            this.otpId = otpId;
            this.code = code;
            this.expiresAt = expiresAt;
            this.attempts = attempts;
        }
    }
}
