package com.menes.banking.auth_service.config.feign;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default(); // Kích hoạt @RequestLine thay cho Spring MVC annotations
    }
}
