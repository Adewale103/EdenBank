package com.twinkles.edenbanks.dtos.responses;

import com.twinkles.edenbanks.data.model.Account;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountInfoResponse {
    private Account account;
    private int statusCode;
    private String message;
    private boolean successful;
}
