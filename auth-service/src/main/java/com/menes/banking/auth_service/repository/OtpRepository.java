package com.menes.banking.auth_service.repository;

import com.menes.banking.auth_service.repository.model.StoredOtp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository  extends CrudRepository<StoredOtp, String> {

    Optional<StoredOtp> findById(String id);

}