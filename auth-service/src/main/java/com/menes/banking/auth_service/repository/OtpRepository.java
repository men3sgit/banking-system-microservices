package com.menes.banking.auth_service.repository;

import com.menes.banking.auth_service.repository.model.StoredOtp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository  extends CrudRepository<StoredOtp, String> {

    void save(String profileId, String otp, java.time.Instant expiresAt);

    Optional<StoredOtp> findById(String id);

    void markUsed(String profileId);

    void increaseAttempt(String profileId);

}