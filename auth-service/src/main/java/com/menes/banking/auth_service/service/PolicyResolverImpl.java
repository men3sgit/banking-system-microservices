package com.menes.banking.auth_service.service;

import com.menes.banking.auth_service.service.OtpPolicy;
import com.menes.banking.auth_service.service.PolicyResolver;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PolicyResolverImpl implements PolicyResolver {

    private final Map<String, OtpPolicy> policies;

    public PolicyResolverImpl(Map<String, OtpPolicy> policies) {
        this.policies = policies;
    }

    @Override
    public OtpPolicy resolve(String type) {
        String key = type.toLowerCase() + "OtpPolicy"; // ví dụ: REGISTER → registerOtpPolicy
        OtpPolicy policy = policies.get(key);
        if (policy == null) {
            throw new IllegalArgumentException("No OTP policy found for type: " + type);
        }
        return policy;
    }
}
