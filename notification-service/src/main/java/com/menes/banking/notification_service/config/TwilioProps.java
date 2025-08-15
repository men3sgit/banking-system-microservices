package com.menes.banking.notification_service.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "twilio")
@Getter
@Setter
public class TwilioProps {

    private String accountSid;

    private String apiKeySid;

    private String authToken;

    private String messagingServiceSid;

    private String fromNumber;

    private String verifyServiceSid;

    @PostConstruct
    public void init() {
        System.err.println("Initializing Twilio...");
        com.twilio.Twilio.init(accountSid, authToken, accountSid);
    }

}
