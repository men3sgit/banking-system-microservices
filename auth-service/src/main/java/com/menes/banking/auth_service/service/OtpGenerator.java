package com.menes.banking.auth_service.service;

import com.menes.banking.auth_service.service.model.OtpContext;

public interface OtpGenerator {

    String generate(String profileId, OtpContext ctx); // ctx: type, metadata

}