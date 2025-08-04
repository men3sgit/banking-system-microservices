package com.menes.banking.account_service.controller.model;

import com.menes.banking.account_service.repository.model.Account.AccountStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private String id;
    private String username;
    private String email;
    private String phone;
    private AccountStatus status;
}
