package com.menes.banking.notification_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

//    @Value("${twilio.account-sid}")
//    private String accountSid;
//
//    @Value("${twilio.auth-token}")
//    private String authToken;
//
//    @Value("${twilio.from-phone}")
//    private String fromPhone;
//
//    /**
//     * Gửi SMS OTP qua Twilio
//     */
    public void sendSms(String toPhone, String messageBody) {
//        Twilio.init(accountSid, authToken);
//        try {
//            Message message = Message.creator(
//                    new PhoneNumber(toPhone),
//                    new PhoneNumber(fromPhone),
//                    messageBody
//            ).create();
//            log.info("SMS sent successfully to {} | SID: {}", toPhone, message.getSid());
//        } catch (Exception e) {
//            log.error("Failed to send SMS to {}: {}", toPhone, e.getMessage());
//            throw new RuntimeException("SMS sending failed", e);
//        }
    }
}
