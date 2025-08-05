package com.menes.banking.profile_service.controller;

import com.menes.banking.profile_service.controller.model.KycRequest;
import com.menes.banking.profile_service.service.ProfileService;
import com.menes.banking.profile_service.service.model.ProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profiles")
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping(path = "/exists/phone")
    public ResponseEntity<?> verifyProfileExistsByPhone(@RequestParam String phone) {
        log.info("Call verifyProfileExists api with phone={}", phone);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping(path = "/exists/email")
    public ResponseEntity<?> verifyProfileExistsByEmail(@RequestParam String email) {
        log.info("Call verifyProfileExists api with email={}", email);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
