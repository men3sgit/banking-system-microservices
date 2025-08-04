package com.menes.banking.account_service.service.impl;

import com.menes.banking.account_service.controller.model.AccountRequest;
import com.menes.banking.account_service.controller.model.AccountResponse;
import com.menes.banking.account_service.repository.AccountRepository;
import com.menes.banking.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public AccountResponse getAccountById(String accountId) {
        return null;
    }

    @Override
    public void createAccount(AccountRequest accountRequest) {

    }
}
