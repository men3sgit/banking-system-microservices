package com.menes.banking.profile_service.config.props;

import lombok.Data;

import java.util.List;

@Data
public class LoginOtpProperties {
    private int length;

    private int ttlInSeconds;

    private int maxAttempts;

    private boolean allowReuse;

    private List<String> channels;
}
