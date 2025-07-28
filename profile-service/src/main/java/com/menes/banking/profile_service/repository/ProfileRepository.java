package com.menes.banking.profile_service.repository;

import com.menes.banking.profile_service.repository.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByProfileId(String profileId);

}
