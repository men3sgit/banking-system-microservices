package com.menes.banking.auth_service.service.model;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OtpContext {

    private String type;

    private Map<String, Object> attrs;
}

