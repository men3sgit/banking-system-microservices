package com.menes.banking.account_service.repository.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account_balances")
public class AccountBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountId; // Liên kết với Account.id

    @Column(nullable = false)
    private BigDecimal availableBalance; // Số dư khả dụng

    @Column(nullable = false)
    private BigDecimal ledgerBalance; // Số dư sổ cái (bao gồm pending transaction)

    @Column(nullable = false)
    private String currency; // VND, USD...
}
