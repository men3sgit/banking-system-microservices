package com.menes.banking.auth_service.repository;

import com.menes.banking.auth_service.repository.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

    Optional<Profile> findByUsername(String username);

    Optional<Profile> findByEmail(String email);

    Optional<Profile> findById(String id);


    boolean existsByEmail(String email);

}
