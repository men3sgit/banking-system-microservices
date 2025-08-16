package com.menes.banking.profile_service.config.props;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rabbit.profile-event")
@Data
public class ProfileEventProps {

    @NotBlank
    private String exchange;

    @NotBlank
    private String queue;

    @NotBlank
    private String routingKey;

}
