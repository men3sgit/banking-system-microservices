package com.menes.banking.notification_service.service.sender.impl;

import java.net.URI;

import com.menes.banking.notification_service.config.TwilioProps;
import com.menes.banking.notification_service.service.sender.SmsSender;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

// If you already have this from earlier:
@Primary
@Slf4j
@Component
@RequiredArgsConstructor
public class TwilioSenderImpl implements SmsSender {

    private final TwilioProps props; // holds accountSid, apiKeySid, apiKeySecret, messagingServiceSid, fromNumber


    @Override
    public void sendSms(String phoneNumber, String otpCode) {
        String to = toE164(phoneNumber);                     // normalize to E.164
        String body = "Hay ngoi day va hoc bai di ban nhe";          // adjust copy as needed

        if (!hasText(props.getMessagingServiceSid()) && !hasText(props.getFromNumber())) {
            throw new IllegalStateException("Configure either twilio.messagingServiceSid or twilio.fromNumber");
        }

        MessageCreator creator = hasText(props.getMessagingServiceSid())
                ? Message.creator(new PhoneNumber(to), props.getMessagingServiceSid(), body)
                : Message.creator(new PhoneNumber(to), new PhoneNumber(props.getFromNumber()), body);

        // Optional: delivery webhook for status tracking
//        creator.setStatusCallback(URI.create("https://your-domain.com/twilio/status-callback"));

        Message msg = creator.create();
        log.info("✅ SMS sent via Twilio. sid={}, to={}", msg.getSid(), to);
    }

    private static boolean hasText(String s) {
        return s != null && !s.isBlank();
    }

    // Minimal E.164 normalizer; for production prefer Google libphonenumber.
    private static String toE164(String raw) {
        String d = raw.replaceAll("[^0-9+]", "");
        if (d.startsWith("+")) return d;
        if (d.startsWith("0")) return "+84" + d.substring(1);   // VN default; change if needed
        throw new IllegalArgumentException("Phone must be E.164 (e.g., +8437xxxxxxx)");
    }
}
