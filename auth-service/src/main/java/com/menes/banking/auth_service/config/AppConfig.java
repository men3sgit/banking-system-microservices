package com.menes.banking.auth_service.config;

import com.menes.banking.auth_service.config.properties.LoginOtpProperties;
import com.menes.banking.auth_service.service.OtpPolicy;
import com.menes.banking.auth_service.service.model.DeliveryChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Slf4j
@EnableConfigurationProperties(LoginOtpProperties.class)
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ExecutorService executorService() {
        log.info("Starting new fixed thread pool");
        return Executors.newFixedThreadPool(2);
    }

    @Bean("registerOtpPolicy")
    public OtpPolicy registerOtpPolicy() {
        log.info("Starting new Registering OTP Policy");
        return new OtpPolicy() {
            public int length() {
                return 6;
            }

            public Duration ttl() {
                return Duration.ofMinutes(3);
            }

            public int maxAttempts() {
                return 5;
            }

            public boolean allowReuse() {
                return false;
            }

            public List<DeliveryChannel> channels() {
                return List.of(DeliveryChannel.SMS);
            }
        };
    }


}
