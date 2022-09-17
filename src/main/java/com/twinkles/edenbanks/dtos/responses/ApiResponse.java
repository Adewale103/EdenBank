package com.twinkles.edenbanks.dtos.responses;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private int statusCode;
    private String message;
    private boolean successful;
}
