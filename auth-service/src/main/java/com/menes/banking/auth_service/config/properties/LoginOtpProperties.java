package com.menes.banking.auth_service.config.properties;

import com.menes.banking.auth_service.service.model.DeliveryChannel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "otp.login")
public class LoginOtpProperties {
    private int length;
    private Duration ttl;
    private int maxAttempts;
    private boolean allowReuse;
    private List<DeliveryChannel> channels;
}
