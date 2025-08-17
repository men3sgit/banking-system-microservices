package com.menes.banking.profile_service.service.impl;

import com.menes.banking.profile_service.controller.model.KycRequest;
import com.menes.banking.profile_service.exception.DomainCode;
import com.menes.banking.profile_service.exception.DomainException;
import com.menes.banking.profile_service.messaging.model.ProfileEvent;
import com.menes.banking.profile_service.repository.ProfileRepository;
import com.menes.banking.profile_service.repository.model.Profile;
import com.menes.banking.profile_service.service.ProfileService;
import com.menes.banking.profile_service.service.model.ProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private static final String PROCESS_NAME = "PROFILE_SERVICE";

    private final ProfileRepository profileRepository;

    @Override
    public void createProfile(ProfileEvent profileEvent) {
        if (profileRepository.findById(profileEvent.getProfileId()).isPresent()) {
            log.error("[{}] Profile already exists with id={}", PROCESS_NAME, profileEvent.getProfileId());
            throw new DomainException(DomainCode.PROFILE_ALREADY_EXISTS);
        }
        Profile newProfile = Profile.from(profileEvent);
        profileRepository.save(newProfile);
        log.info("[{}] Profile created", PROCESS_NAME);

        // TODO: publish profile created event to KYCService
    }

    @Override
    public void updateProfile(ProfileEvent profileEvent) {
        log.info("[{}] Updating profile with id={}", PROCESS_NAME, profileEvent.getProfileId());
        Profile newProfile = Profile.from(profileEvent);
        profileRepository.save(newProfile);
        log.info("[{}] Profile updated", PROCESS_NAME);
    }

    @Override
    public void submitKyc(String profileId, KycRequest request) {

    }

    @Override
    public ProfileResponse getProfile(String profileId) {
        return null;
    }

    @Override
    public List<ProfileResponse> getPendingKycs() {
        return List.of();
    }

    @Override
    public void approveKyc(String profileId) {

    }

    @Override
    public void rejectKyc(String profileId, String reason) {

    }


}
