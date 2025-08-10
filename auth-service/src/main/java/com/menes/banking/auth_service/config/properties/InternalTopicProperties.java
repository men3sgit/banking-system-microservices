package com.menes.banking.auth_service.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "messaging.destination")
@Getter
@Setter
public class InternalTopicProperties {

    private String profileTopicName;

    private String notificationTopicName;

    private String accountTopicName;

}
