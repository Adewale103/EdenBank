package com.twinkles.edenbanks.services;

import com.twinkles.edenbanks.data.model.Account;
import com.twinkles.edenbanks.data.repository.AccountRepository;
import com.twinkles.edenbanks.data.repository.AccountRepositoryImpl;
import com.twinkles.edenbanks.dtos.requests.CreateAccountRequest;
import com.twinkles.edenbanks.dtos.requests.DepositRequest;
import com.twinkles.edenbanks.dtos.responses.ApiResponse;
import com.twinkles.edenbanks.dtos.responses.GetAccountInfoResponse;
import com.twinkles.edenbanks.dtos.responses.TransactionResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceImplTest {
    private final AccountRepository accountRepository = new AccountRepositoryImpl();
    private final AccountService accountService = new AccountServiceImpl(accountRepository);
    private CreateAccountRequest createAccountRequest;
    private DepositRequest depositRequest;

    @BeforeEach
    void setUp() {
        createAccountRequest = CreateAccountRequest.builder()
                .accountName("Adewale Adeyinka")
                .accountPassword("1234")
                .InitialDeposit(6700)
                .build();
    }
    @Test
    public void createAccountRequest(){
        ApiResponse apiResponse = accountService.createAccount(createAccountRequest);
        assertEquals(1, accountService.size());
        assertTrue(apiResponse.isSuccessful());
        assertEquals(200,apiResponse.getStatusCode());
    }

    @Test
    public void getAccountInfoTest(){
        accountService.createAccount(createAccountRequest);
        Account account = accountRepository.findAccountByAccountName("Adewale Adeyinka");
        GetAccountInfoResponse getAccountInfoResponse = accountService.getAccountInfo(account.getAccountNumber());
        assertEquals(200, getAccountInfoResponse.getStatusCode());
    }
    @Test
    public void getAccountStatementTest(){
        accountService.createAccount(createAccountRequest);
        Account account = accountRepository.findAccountByAccountName("Adewale Adeyinka");
        List<TransactionResponseDto> transactionResponseDtoList = accountService.getAccountStatement(account.getAccountNumber());
    }

    @Test
    public void accountCanDepositTest(){
        accountService.createAccount(createAccountRequest);
        Account account = accountRepository.findAccountByAccountName("Adewale Adeyinka");
        ApiResponse apiResponse = accountService.deposit(depositRequest);
        assertEquals(200, apiResponse.getStatusCode());

    }
}