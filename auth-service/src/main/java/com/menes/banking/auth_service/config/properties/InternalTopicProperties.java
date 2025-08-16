package com.menes.banking.auth_service.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "messaging.destination")
@Data
public class InternalTopicProperties {

    private String profileTopicName;

    private String notificationTopicName;

    private String accountTopicName;

}
