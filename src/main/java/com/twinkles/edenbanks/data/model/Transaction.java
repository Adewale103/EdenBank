package com.twinkles.edenbanks.data.model;

import com.twinkles.edenbanks.data.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String id;
    private String narration;
    private BigDecimal amount;
    private BigDecimal balanceAfterTransaction;
    private TransactionType transactionType;
    private LocalDateTime transactionDate;
}
