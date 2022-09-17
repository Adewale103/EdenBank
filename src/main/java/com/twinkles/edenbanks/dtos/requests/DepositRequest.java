package com.twinkles.edenbanks.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositRequest {
    private String accountNumber;
    private double amount;
}
