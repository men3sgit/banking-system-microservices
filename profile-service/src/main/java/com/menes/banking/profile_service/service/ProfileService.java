package com.menes.banking.profile_service.service;

import com.menes.banking.profile_service.controller.model.KycRequest;
import com.menes.banking.profile_service.messaging.model.ProfileEvent;
import com.menes.banking.profile_service.service.model.ProfileResponse;

import java.util.List;

public interface ProfileService {

    void createProfile(ProfileEvent profileEvent);

    void submitKyc(String profileId, KycRequest request);

    ProfileResponse getProfile(String userId);

    List<ProfileResponse> getPendingKycs();

    void approveKyc(String profileId);

    void rejectKyc(String profileId, String reason);

}