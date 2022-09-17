package com.twinkles.edenbanks.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawRequest {
    private String accountNumber;
    private String password;
    private double withdrawAmount;
}
