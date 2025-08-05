package com.menes.banking.auth_service.connector;

import com.menes.banking.auth_service.config.feign.FeignConfig;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "profile-service", url = "${services.profile.url}", configuration = FeignConfig.class)
public interface ProfileClient {

    @RequestLine("GET /api/v1/profiles/exists/phone?phone={phone}")
    Boolean verifyProfileExistsByPhone(@Param("phone") String phone);

    @RequestLine("GET /api/v1/profiles/exists/email?email={email}")
    Boolean verifyProfileExistsByEmail(@Param("email") String email);
}
