package com.menes.banking.auth_service.repository.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class StoredOtp {

    private String code;

    private String type;

    private Instant expiresAt;

    private int attempts;

    private boolean used;

    private String metadataJson;

}
