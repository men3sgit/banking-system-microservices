package com.menes.banking.profile_service.controller;

import com.menes.banking.profile_service.controller.model.KycRequest;
import com.menes.banking.profile_service.service.ProfileService;
import com.menes.banking.profile_service.service.model.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/{profileId}/kyc")
    public ResponseEntity<String> submitKyc(@PathVariable String profileId, @RequestBody KycRequest request) {
        profileService.submitKyc(profileId, request);
        return ResponseEntity.ok("KYC submitted successfully. Waiting for admin approval.");
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable String profileId) {
        return ResponseEntity.ok(profileService.getProfile(profileId));
    }

    @GetMapping("/admin/kyc/pending")
    public ResponseEntity<List<ProfileResponse>> getPendingKycs() {
        return ResponseEntity.ok(profileService.getPendingKycs());
    }

    @PostMapping("/admin/kyc/{profileId}/approve")
    public ResponseEntity<String> approveKyc(@PathVariable String profileId) {
        profileService.approveKyc(profileId);
        return ResponseEntity.ok("KYC approved successfully.");
    }

    @PostMapping("/admin/kyc/{profileId}/reject")
    public ResponseEntity<String> rejectKyc(@PathVariable String profileId, @RequestBody String reason) {
        profileService.rejectKyc(profileId, reason);
        return ResponseEntity.ok("KYC rejected: " + reason);
    }
}
