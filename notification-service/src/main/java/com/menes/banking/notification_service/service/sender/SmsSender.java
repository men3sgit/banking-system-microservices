package com.menes.banking.notification_service.service.sender;

import org.springframework.stereotype.Component;

@Component
public class SmsSender {

    public void sendSms(String phoneNumber, String otpCode) {
        // Gửi SMS đơn giản (Mock)
        System.out.printf("📩 Sending SMS to %s: OTP = %s%n", phoneNumber, otpCode);
    }
}
