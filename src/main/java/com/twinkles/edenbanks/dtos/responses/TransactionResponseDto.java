package com.twinkles.edenbanks.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDto {
    private String transactionDate;
    private String transactionType;
    private String narration;
    private double amount;
    private double balanceAfterTransaction;
}
