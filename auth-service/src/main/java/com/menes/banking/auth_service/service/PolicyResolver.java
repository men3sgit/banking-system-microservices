package com.menes.banking.auth_service.service;

public interface PolicyResolver {

    OtpPolicy resolve(String type);

}
