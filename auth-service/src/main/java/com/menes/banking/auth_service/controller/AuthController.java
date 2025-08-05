package com.menes.banking.auth_service.controller;

import com.menes.banking.auth_service.dto.*;
import com.menes.banking.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody LogoutRequest request) {
        authService.logout(request);
        return ResponseEntity.ok("Logged out successfully.");
    }

    @PostMapping(path = "/test-register")
    public ResponseEntity<?> testFeignClient(@RequestBody String request) {
        log.info("Call test feign client from auth service with request {}", request);
        authService.callProfileClient(request);
        return ResponseEntity.ok("OKEEEE");
    }

    @GetMapping(path = "/hello")
    public String helloWorld(){
        return "Hello World";
    }
}

