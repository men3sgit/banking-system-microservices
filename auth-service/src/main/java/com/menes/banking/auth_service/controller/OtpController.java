package com.menes.banking.auth_service.controller;


import com.menes.banking.auth_service.controller.model.OtpRequest;
import com.menes.banking.auth_service.controller.model.OtpResendRequest;
import com.menes.banking.auth_service.controller.model.OtpResendResponse;
import com.menes.banking.auth_service.controller.model.OtpVerifyResponse;
import com.menes.banking.auth_service.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth/otp")
public class OtpController {

    private final OtpService otpService;

    @PostMapping(path = "/verify")
    public ResponseEntity<OtpVerifyResponse> verifyOtp(@Valid @RequestBody OtpRequest request) {
        boolean ok = otpService.validate(request);

        if (ok) {
            return ResponseEntity.ok(new OtpVerifyResponse(true, "OTP valid"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new OtpVerifyResponse(false, "OTP invalid or expired"));
    }

    @PostMapping(path = "/resend")
    public ResponseEntity<OtpResendResponse> resend(@Valid @RequestBody OtpResendRequest req) {
        var result = otpService.resend(req.getOtpId());
        return ResponseEntity.ok(new OtpResendResponse(
                result.getOtpId(),
                result.getExpiresAt(),
                "OTP resent"
        ));
    }
}
