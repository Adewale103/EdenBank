package com.twinkles.edenbanks.services;

import com.twinkles.edenbanks.data.model.Account;
import com.twinkles.edenbanks.data.model.Transaction;
import com.twinkles.edenbanks.data.model.enums.TransactionType;
import com.twinkles.edenbanks.data.repository.AccountRepository;
import com.twinkles.edenbanks.dtos.requests.CreateAccountRequest;
import com.twinkles.edenbanks.dtos.requests.DepositRequest;
import com.twinkles.edenbanks.dtos.responses.ApiResponse;
import com.twinkles.edenbanks.dtos.responses.GetAccountInfoResponse;
import com.twinkles.edenbanks.dtos.responses.TransactionResponseDto;
import com.twinkles.edenbanks.exceptions.AccountAlreadyExistException;
import com.twinkles.edenbanks.exceptions.AccountNotFoundException;
import com.twinkles.edenbanks.exceptions.InitialDepositNotValidException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ApiResponse createAccount(CreateAccountRequest createAccountRequest) {
        validateThatAccountDoesNotExist(createAccountRequest);
        validateInitialDeposit(createAccountRequest);
        Account createdAccount = buildAccountFrom(createAccountRequest);
        accountRepository.save(createdAccount);
        return ApiResponse.builder()
                .message("Account with account name "+createdAccount.getAccountName()+" successfully created")
                .statusCode(200)
                .successful(true)
                .build();

    }

    @Override
    public GetAccountInfoResponse getAccountInfo(String accountNumber) {
        Account foundAccount = accountRepository.findAccountByAccountNumber(accountNumber);
        checkThatAccountExist(foundAccount);
        return  GetAccountInfoResponse.builder()
                .account(foundAccount)
                .message("Account details found!")
                .statusCode(200)
                .successful(true)
                .build();
    }

    private void checkThatAccountExist(Account foundAccount) {
        if(foundAccount == null){
            throw new AccountNotFoundException("Account not found!", 404);
        }
    }

    @Override
    public List<TransactionResponseDto> getAccountStatement(String accountNumber) {
        Account foundAccount = accountRepository.findAccountByAccountNumber(accountNumber);
        checkThatAccountExist(foundAccount);
        List<Transaction> transactionList = foundAccount.getTransactions();
        return transactionList.stream().map(this::buildTransactionResponseDto).collect(Collectors.toList());
    }

    @Override
    public ApiResponse deposit(DepositRequest depositRequest) {
        Account foundAccount = accountRepository.findAccountByAccountNumber(depositRequest.getAccountNumber());
        checkThatAccountExist(foundAccount);
        validateDeposit(depositRequest);
        BigDecimal balance = foundAccount.getAccountBalance().add(BigDecimal.valueOf(depositRequest.getAmount()));
        foundAccount.setAccountBalance(balance);
        createTransaction(depositRequest, balance);
        accountRepository.save(foundAccount);
        return ApiResponse.builder()
                .successful(true)
                .message("Deposit request was successful!")
                .statusCode(200)
                .build();

    }

    private void createTransaction(DepositRequest depositRequest, BigDecimal balance) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(BigDecimal.valueOf(depositRequest.getAmount()));
        transaction.setBalanceAfterTransaction(balance);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setNarration("Deposit of "+ depositRequest.getAmount()+" was made into "+ depositRequest.getAccountNumber());
    }

    private TransactionResponseDto buildTransactionResponseDto(Transaction transaction) {
        return TransactionResponseDto.builder().transactionDate(formatDateToString(transaction.getTransactionDate())).
                transactionType(String.valueOf(transaction.getTransactionType())).balanceAfterTransaction(transaction.getBalanceAfterTransaction().doubleValue())
                .amount(transaction.getAmount().doubleValue()).narration(transaction.getNarration()).build();
    }

    @Override
    public int size() {
        return accountRepository.size();
    }



    private Account buildAccountFrom(CreateAccountRequest createAccountRequest) {
        return Account.builder()
                .accountName(createAccountRequest.getAccountName())
                .accountBalance(BigDecimal.valueOf(createAccountRequest.getInitialDeposit()))
                .accountNumber(generateAccountNumber())
                .accountPassword(createAccountRequest.getAccountPassword())
                .id(generateId())
                .transactions(new ArrayList<>())
                .build();
    }

    private void validateThatAccountDoesNotExist(CreateAccountRequest createAccountRequest) {
        if(accountRepository.findAccountByAccountName(createAccountRequest.getAccountName())!= null) {
            throw new AccountAlreadyExistException("Account " + createAccountRequest.getAccountName() + " already exist", 400);
        }
    }
    private void validateInitialDeposit(CreateAccountRequest createAccountRequest) {
        if(createAccountRequest.getInitialDeposit() < 500 || createAccountRequest.getInitialDeposit() >= 1000000) {
            throw new InitialDepositNotValidException(createAccountRequest.getInitialDeposit() + "is not within the amount that can be deposited", 400);
        }
    }

    private void validateDeposit(DepositRequest depositRequest) {
        if(depositRequest.getAmount() < 1 || depositRequest.getAmount() > 1000000) {
            throw new InitialDepositNotValidException(depositRequest.getAmount() + "is not within the amount that can be deposited", 400);
        }
    }
    private String generateAccountNumber(){
        return UUID.randomUUID().toString().substring(0,10);
    }
    private String generateId(){
        return String.valueOf(accountRepository.size());
    }
    private String formatDateToString(LocalDateTime date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, dd-MM-yyyy, hh-mm-ss, a");
        return dateTimeFormatter.format(date);
    }
}
