package com.menes.banking.account_service.controller;

import com.menes.banking.account_service.controller.model.AccountResponse;
import com.menes.banking.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping(path = "/{accountId}")
    public ResponseEntity<AccountResponse> getAccountInfo(@PathVariable String accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }
}