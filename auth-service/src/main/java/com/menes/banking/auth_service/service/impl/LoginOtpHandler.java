package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.repository.OtpRepository;
import com.menes.banking.auth_service.service.OtpGenerator;
import com.menes.banking.auth_service.service.OtpHandler;
import com.menes.banking.auth_service.service.OtpPolicy;
import com.menes.banking.auth_service.service.OtpValidator;
import com.menes.banking.auth_service.service.model.OtpContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoginOtpHandler implements OtpHandler {

    private final OtpGenerator gen;

    private final OtpValidator val;

    private final OtpPolicy policy;

    private final OtpRepository otpRepository;


    @Override
    public String type() {
        return "LOGIN";
    }

    @Override
    public String generate(String profileId, OtpContext ctx) {
        var code = gen.generate(profileId, ctx);
        var expiresAt = java.time.Instant.now().plus(policy.ttl());
        otpRepository.save(profileId, code, ctx, expiresAt);
        return code;
    }

    @Override
    public boolean validate(String profileId, String otp, OtpContext ctx) {
        return val.validate(profileId, otp, ctx);
    }
}