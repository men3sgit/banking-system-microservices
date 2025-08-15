package com.menes.banking.notification_service.service.impl;

import com.menes.banking.notification_service.service.ContactService;
import com.menes.banking.notification_service.service.NotificationService;
import com.menes.banking.notification_service.service.sender.SmsSender;
import com.menes.banking.notification_service.service.sender.impl.MockSmsSenderImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final SmsSender smsSender;
    private final ContactService contactService;


    @Override
    public void sendOtp(String profileId, String otpCode, String channel) {
        switch (channel.toUpperCase()) {
            case "SMS" -> {
                String phone = contactService.getPhoneNumber(profileId);
                smsSender.sendSms(phone, otpCode);
            }
            case "EMAIL" -> {
                System.out.println("EMAIL HANDLE OTP PROCESS");
                ;
                log.info("sending email");
            }

            default -> System.err.println("⚠ Unsupported channel in this simple version: " + channel);
        }
    }

    @Override
    public void sendTestOtp(String message) {
        smsSender.sendSms(MockSmsSenderImpl.MOCK_PHONE_NUMBER, message);
    }

}
