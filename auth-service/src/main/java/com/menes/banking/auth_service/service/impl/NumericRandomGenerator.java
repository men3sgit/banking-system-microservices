package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.service.OtpGenerator;
import com.menes.banking.auth_service.service.OtpPolicy;
import com.menes.banking.auth_service.service.PolicyResolver;
import com.menes.banking.auth_service.service.model.OtpContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class NumericRandomGenerator implements OtpGenerator {

    private final PolicyResolver policyResolver;
    // Có thể inject từ @Bean trong AppConfig, hoặc dùng new SecureRandom() trực tiếp.
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generate(String profileId, OtpContext ctx) {
        OtpPolicy policy = policyResolver.resolve(ctx.getType());
        int len = policy.length();
        if (len <= 0) {
            throw new IllegalArgumentException("OTP length must be > 0");
        }

        // Sinh chuỗi số [0-9] độ dài len (cho phép 0 ở đầu)
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(secureRandom.nextInt(10)); // 0..9
        }
        return sb.toString();
    }
}
