package com.menes.banking.auth_service.service.impl;

import com.menes.banking.auth_service.service.OtpHandler;
import com.menes.banking.auth_service.service.OtpService;
import com.menes.banking.auth_service.service.model.OtpContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final Map<String, OtpHandler> byType;

    @Override
    public String generateOtp(String profileId, String type, Map<String,Object> attrs) {
        return require(type).generate(profileId, new OtpContext(type, attrs));
    }


    @Override
    public boolean validateOtp(String profileId, String type, String otp) {
        return require(type).validate(profileId, otp, new OtpContext(type, Map.of()));
    }

    private OtpHandler require(String type) {
        var h = byType.get(type);
        if (h == null) throw new IllegalArgumentException("Unsupported OTP type: " + type);
        return h;
    }
}
