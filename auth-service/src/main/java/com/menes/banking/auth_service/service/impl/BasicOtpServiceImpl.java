package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.controller.model.OtpRequest;
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
    private static final int MAX_RESENDS = 3;       // policy: gửi lại tối đa 3 lần

    private static final SecureRandom random = new SecureRandom();

    private final Map<String, Entry> store = new ConcurrentHashMap<>();

    @Override
    public OtpResult generateOtp() {
        String code = generateCode(OTP_LENGTH);
        String otpId = UUID.randomUUID().toString();
        Instant exp = Instant.now().plusSeconds(TTL_SECONDS);

        store.put(otpId, new Entry(otpId, code, exp, 0, 0)); // enhance later because override otp with the same subject

        return OtpResult.builder()
                .otpId(otpId)
                .code(code)
                .expiresAt(exp)
                .build();
    }

    @Override
    public boolean validate(OtpRequest request) {
        final String otpId = request.getOtpId(); // <-- sửa tên getter
        final String input = request.getOtpCode();

        return store.compute(otpId, (id, e) -> {
            if (e == null) return null; // không tồn tại

            Instant now = Instant.now();
            if (now.isAfter(e.expiresAt) || e.attempts >= MAX_ATTEMPTS) {
                return null; // xoá luôn nếu hết hạn hoặc vượt quá số lần thử
            }

            e.attempts++; // tăng đếm trong vùng atomic
            boolean ok = e.code.equals(input);
            return ok ? null : e; // đúng thì remove (return null), sai thì giữ lại với attempts đã +1
        }) == null && !store.containsKey(otpId);
    }

    @Override
    public OtpResult resend(String otpId) {
        Entry e = store.compute(otpId, (id, cur) -> {
            if (cur == null) return null;
            Instant now = Instant.now();
            if (now.isAfter(cur.expiresAt)) return null;
            if (cur.resends >= MAX_RESENDS) return cur; // giữ nguyên nếu quá giới hạn

            // rotate code + reset attempts + extend TTL
            cur.rotateCode();
            cur.resends++;
            return cur;
        });

        if (e == null) {
            throw new IllegalStateException("OTP not found or expired");
        }
        if (e.resends > MAX_RESENDS) { // phòng ngừa, thực tế không vào nhánh này
            throw new IllegalStateException("Resend limit exceeded");
        }
        // Trả về code chỉ dùng DEV. Prod: gửi code qua kênh SMS/Email, không trả trong API.
        return OtpResult.builder()
                .otpId(e.otpId)
                .code(e.code)
                .expiresAt(e.expiresAt)
                .build();
    }


    private static String generateCode(int len) {
        int min = (int) Math.pow(10, len - 1); // 10^(len-1)
        int range = 9 * min;                   // từ min đến 10^len - 1
        int n = random.nextInt(range) + min;
        return Integer.toString(n);
    }



    // Dọn rác định kỳ (tuỳ chọn)
    @Scheduled(fixedDelay = 60_000)
    public void evictExpired() {
        Instant now = Instant.now();
        store.entrySet().removeIf(e -> now.isAfter(e.getValue().expiresAt));
    }

    @Getter
    private static final class Entry {
        String code;
        Instant expiresAt;
        int attempts;
        final String otpId;
        int resends;    // số lần gửi lại

        Entry(String otpId, String code, Instant expiresAt, int attempts, int resends) {
            this.otpId = otpId;
            this.code = code;
            this.expiresAt = expiresAt;
            this.attempts = attempts;
            this.resends = resends;
        }

        public void rotateCode() {
            this.code = generateCode(OTP_LENGTH);
            this.attempts = 0;
            this.expiresAt = Instant.now().plusSeconds(TTL_SECONDS);

        }
    }
}
