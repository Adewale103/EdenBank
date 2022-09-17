package com.twinkles.edenbanks.services;

import com.twinkles.edenbanks.data.model.Account;
import com.twinkles.edenbanks.data.repository.AccountRepository;
import com.twinkles.edenbanks.data.repository.AccountRepositoryImpl;
import com.twinkles.edenbanks.data.repository.UserRepository;
import com.twinkles.edenbanks.data.repository.UserRepositoryImpl;
import com.twinkles.edenbanks.dtos.requests.CreateAccountRequest;
import com.twinkles.edenbanks.dtos.requests.DepositRequest;
import com.twinkles.edenbanks.dtos.requests.RegisterUserRequest;
import com.twinkles.edenbanks.dtos.requests.WithdrawRequest;
import com.twinkles.edenbanks.dtos.responses.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    private final AccountRepository accountRepository = new AccountRepositoryImpl();
    private final AccountService accountService = new AccountServiceImpl(accountRepository);
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserService userService = new UserServiceImpl(userRepository,accountService, bCryptPasswordEncoder);

    private DepositRequest depositRequest;
    private WithdrawRequest withdrawRequest;
    private ApiResponse apiResponse;
    private RegisterUserRequest registerUserRequest;
    private  CreateAccountRequest createAccountRequest;
    @BeforeEach
    void setUp() {
        registerUserRequest = RegisterUserRequest.builder()
                .firstName("Adewale")
                .lastName("Adeyinka")
                .email("adeyinka@gmail.com")
                .initialDeposit(6700)
                .accountPassword("1234")
                .build();
        apiResponse = userService.registerUserAccount(registerUserRequest);

        Account account = accountRepository.findAccountByAccountName("Adewale Adeyinka");

        createAccountRequest = CreateAccountRequest.builder()
                .accountName("Adewale Adeyinka")
                .accountPassword("1234")
                .InitialDeposit(6700)
                .build();

        depositRequest = DepositRequest.builder()
                .accountNumber(account.getAccountNumber())
                .amount(4242)
                .build();
        withdrawRequest = WithdrawRequest.builder()
                .accountNumber(account.getAccountNumber())
                .withdrawAmount(1000)
                .password("1234")
                .build();

    }
    @Test
    public void RegisterUserTest(){
        assertEquals(200,apiResponse.getStatusCode());
    }



}