package com.twinkles.edenbanks.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String accountPassword;
    private double initialDeposit;
}
