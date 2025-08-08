package com.menes.banking.auth_service.repository;

import com.menes.banking.auth_service.repository.model.StoredOtp;
import com.menes.banking.auth_service.service.model.OtpContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository  extends CrudRepository<StoredOtp, String> {

    void save(String profileId, String otp, OtpContext ctx, java.time.Instant expiresAt);

    Optional<StoredOtp> find(String profileId, OtpContext ctx);

    void markUsed(String profileId, OtpContext ctx);

    void increaseAttempt(String profileId, OtpContext ctx);

}