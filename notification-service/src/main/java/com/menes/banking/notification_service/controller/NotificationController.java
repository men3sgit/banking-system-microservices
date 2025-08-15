package com.menes.banking.notification_service.controller;

import com.menes.banking.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/test-notify")
    public ResponseEntity<String> testNotify(String message) {
        notificationService.sendTestOtp(message);
        return ResponseEntity.ok(message);
    }
}
