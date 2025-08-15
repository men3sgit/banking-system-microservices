package com.menes.banking.notification_service.service.sender.impl;

import com.menes.banking.notification_service.service.sender.SmsSender;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class MockSmsSenderImpl implements SmsSender {
    public static final String MOCK_PHONE_NUMBER = "0377779443";

    @Override
    public void sendSms(String phoneNumber, String otpCode) {
        // Gửi SMS đơn giản (Mock)
        System.out.printf("📩 Sending SMS to %s: OTP = %s%n", phoneNumber, otpCode);

    }
}
