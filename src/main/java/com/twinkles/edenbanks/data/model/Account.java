package com.twinkles.edenbanks.data.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private String id;
    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String accountPassword;
    private List<Transaction> transactions;

    public Account(String accountName, String accountNumber, BigDecimal accountBalance, String accountPassword) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.accountPassword = accountPassword;
        transactions = new ArrayList<>();
    }
}
