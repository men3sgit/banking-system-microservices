package com.menes.banking.auth_service.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoredOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String code;

    private String type;

    private Instant expiresAt;

    private int attempts;

    private boolean used;

    private String metadataJson;

}
