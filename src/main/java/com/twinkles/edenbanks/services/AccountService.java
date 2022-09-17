package com.twinkles.edenbanks.services;

import com.twinkles.edenbanks.dtos.requests.CreateAccountRequest;
import com.twinkles.edenbanks.dtos.requests.DepositRequest;
import com.twinkles.edenbanks.dtos.requests.WithdrawRequest;
import com.twinkles.edenbanks.dtos.responses.ApiResponse;
import com.twinkles.edenbanks.dtos.responses.GetAccountInfoResponse;
import com.twinkles.edenbanks.dtos.responses.TransactionResponseDto;

import java.util.List;

public interface AccountService {
    ApiResponse createAccount(CreateAccountRequest createAccountRequest);
    int size();
    GetAccountInfoResponse getAccountInfo(String accountNumber);
    List<TransactionResponseDto> getAccountStatement(String accountNumber);

    ApiResponse deposit(DepositRequest depositRequest);

    ApiResponse withdraw(WithdrawRequest withdrawRequest);
}
