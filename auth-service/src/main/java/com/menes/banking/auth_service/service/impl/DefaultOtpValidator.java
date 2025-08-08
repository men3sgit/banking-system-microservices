package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.repository.OtpRepository;
import com.menes.banking.auth_service.service.OtpValidator;
import com.menes.banking.auth_service.service.PolicyResolver;
import com.menes.banking.auth_service.service.model.OtpContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor

@Component
public class DefaultOtpValidator implements OtpValidator {
    private final OtpRepository otpRepository;
    private final PolicyResolver resolver;

    public boolean validate(String profileId, String otp, OtpContext ctx) {
        var policy = resolver.resolve(ctx.getType());
        var stored = otpRepository.find(profileId, ctx).orElse(null);
        if (stored == null) return false;

        // check expiry
        if (java.time.Instant.now().isAfter(stored.getExpiresAt())) return false;

        // check attempts
        if (stored.getAttempts() >= policy.maxAttempts()) return false;

        // match
        boolean ok = stored.getCode().equals(otp);

        otpRepository.increaseAttempt(profileId, ctx);
        if (ok && !policy.allowReuse()) otpRepository.markUsed(profileId, ctx);

        return ok;
    }

}