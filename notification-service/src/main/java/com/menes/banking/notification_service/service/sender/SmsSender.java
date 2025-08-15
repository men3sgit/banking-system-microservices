package com.menes.banking.notification_service.service.sender;


public interface SmsSender {

    void sendSms(String phoneNumber, String otpCode);
}
