package com.twinkles.edenbanks.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest {
    private String accountName;
    private String accountPassword;
    private double InitialDeposit;
}
