package com.menes.banking.account_service.service;

import com.menes.banking.account_service.controller.model.AccountRequest;
import com.menes.banking.account_service.controller.model.AccountResponse;

public interface AccountService {
    AccountResponse getAccountById(String accountId);

    void createAccount(AccountRequest accountRequest);
}
