package com.twinkles.edenbanks.controller;

import com.twinkles.edenbanks.data.model.Account;
import com.twinkles.edenbanks.dtos.requests.DepositRequest;
import com.twinkles.edenbanks.dtos.requests.WithdrawRequest;
import com.twinkles.edenbanks.dtos.responses.ApiResponse;
import com.twinkles.edenbanks.dtos.responses.GetAccountInfoResponse;
import com.twinkles.edenbanks.dtos.responses.TransactionResponseDto;
import com.twinkles.edenbanks.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/account")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account_info/{accountNumber}")
    public ResponseEntity<?>getAccountInfo(@PathVariable("accountNumber") String accountNumber){
        GetAccountInfoResponse accountInfoResponse = accountService.getAccountInfo(accountNumber);
        return new ResponseEntity<>(accountInfoResponse, HttpStatus.OK);
    }

    @GetMapping("/account_statement/{accountNumber}")
    public ResponseEntity<?>getAccountBalance(@PathVariable("accountNumber") String accountNumber){
        List<TransactionResponseDto> transactionResponseDtoList = accountService.getAccountStatement(accountNumber);
        return new ResponseEntity<>(transactionResponseDtoList,HttpStatus.OK);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?>makeWithdrawal(@RequestBody WithdrawRequest withdrawRequest){
        ApiResponse apiResponse = accountService.withdraw(withdrawRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?>makeDeposit(@RequestBody DepositRequest depositRequest){
        ApiResponse apiResponse = accountService.deposit(depositRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{accountName}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?>findAccountByAccountName(@PathVariable("accountName") String accountName){
        Account account = accountService.findAccountByAccountName(accountName);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
