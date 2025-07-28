package com.menes.banking.profile_service.service.impl;

import com.menes.banking.profile_service.controller.model.KycRequest;
import com.menes.banking.profile_service.service.ProfileService;
import com.menes.banking.profile_service.service.model.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    @Override
    public void submitKyc(String profileId, KycRequest request) {

    }

    @Override
    public ProfileResponse getProfile(String userId) {
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
