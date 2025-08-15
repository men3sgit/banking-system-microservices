package com.menes.banking.notification_service.service;

public interface NotificationService {
    void sendOtp(String profileId, String otpCode, String channel);

    void sendTestOtp(String phoneNumber);
}
