package com.menes.banking.auth_service.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequest {

    private String otpId;

    private String otpCode;

    private String channel;

    private String purpose;

}
