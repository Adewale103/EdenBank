package com.twinkles.edenbanks.services;

import com.twinkles.edenbanks.data.model.Account;
import com.twinkles.edenbanks.data.model.Transaction;
import com.twinkles.edenbanks.data.model.enums.TransactionType;
import com.twinkles.edenbanks.data.repository.AccountRepository;
import com.twinkles.edenbanks.dtos.requests.CreateAccountRequest;
import com.twinkles.edenbanks.dtos.requests.DepositRequest;
import com.twinkles.edenbanks.dtos.requests.WithdrawRequest;
import com.twinkles.edenbanks.dtos.responses.ApiResponse;
import com.twinkles.edenbanks.dtos.responses.GetAccountInfoResponse;
import com.twinkles.edenbanks.dtos.responses.TransactionResponseDto;
import com.twinkles.edenbanks.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private static int accountIdCounter;
    private static int transactionIdCounter;
    private SecureRandom secureRandom = new SecureRandom();

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ApiResponse createAccount(CreateAccountRequest createAccountRequest) {
        validateThatAccountDoesNotExist(createAccountRequest);
        log.info("initial amount -> {}",createAccountRequest.getInitialDeposit());
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
        Transaction creditTransaction = createDepositTransaction(depositRequest, balance, foundAccount);
        foundAccount.getTransactions().add(creditTransaction);
        accountRepository.save(foundAccount);
        return ApiResponse.builder()
                .successful(true)
                .message("Deposit request was successful!")
                .statusCode(200)
                .build();

    }

    @Override
    public ApiResponse withdraw(WithdrawRequest withdrawRequest) {
        Account foundAccount = accountRepository.findAccountByAccountNumber(withdrawRequest.getAccountNumber());
        checkThatAccountExist(foundAccount);
        validateWithdrawal(withdrawRequest, foundAccount);
        BigDecimal balance = foundAccount.getAccountBalance().subtract(BigDecimal.valueOf(withdrawRequest.getWithdrawAmount()));
        foundAccount.setAccountBalance(balance);
        Transaction debitTransaction = createWithdrawalTransaction(withdrawRequest, balance, foundAccount);
        foundAccount.getTransactions().add(debitTransaction);
        accountRepository.save(foundAccount);
        return ApiResponse.builder()
                .successful(true)
                .message("Withdrawal request was successful!")
                .statusCode(200)
                .build();

    }

    @Override
    public Account findAccountByAccountName(String accountName) {
        Account account = accountRepository.findAccountByAccountName(accountName);
        checkThatAccountExist(account);
        return account;
    }


    @Override
    public int size() {
        return accountRepository.size();
    }

    private void checkThatAccountExist(Account foundAccount) {
        if(foundAccount == null){
            throw new AccountNotFoundException("Account not found!", 404);
        }
    }

    private Transaction createDepositTransaction(DepositRequest depositRequest, BigDecimal balance, Account account) {
        Transaction transaction = new Transaction();
        transaction.setId(generateTransactionId());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(BigDecimal.valueOf(depositRequest.getAmount()));
        transaction.setBalanceAfterTransaction(balance);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setNarration("Deposit of "+ depositRequest.getAmount()+" was made into "+ depositRequest.getAccountNumber());
        return transaction;
    }

    private Transaction createWithdrawalTransaction(WithdrawRequest withdrawRequest, BigDecimal balance, Account account) {
        Transaction transaction = new Transaction();
        transaction.setId(generateTransactionId());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(BigDecimal.valueOf(withdrawRequest.getWithdrawAmount()));
        transaction.setBalanceAfterTransaction(balance);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setNarration("Withdrawal of "+ withdrawRequest.getAccountNumber()+" was made from your account");
        return transaction;
    }

    private TransactionResponseDto buildTransactionResponseDto(Transaction transaction) {
        return TransactionResponseDto.builder().transactionDate(formatDateToString(transaction.getTransactionDate())).
                transactionType(String.valueOf(transaction.getTransactionType())).balanceAfterTransaction(transaction.getBalanceAfterTransaction().doubleValue())
                .amount(transaction.getAmount().doubleValue()).narration(transaction.getNarration()).build();
    }

    private Account buildAccountFrom(CreateAccountRequest createAccountRequest) {
        return Account.builder()
                .accountName(createAccountRequest.getAccountName())
                .accountBalance(BigDecimal.valueOf(createAccountRequest.getInitialDeposit()))
                .accountNumber(generateAccountNumber())
                .accountPassword(createAccountRequest.getAccountPassword())
                .id(generateAccountId())
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
            throw new DepositNotValidException(createAccountRequest.getInitialDeposit() + " is not within the amount that can be deposited", 400);
        }
    }

    private void validateDeposit(DepositRequest depositRequest) {
        if(depositRequest.getAmount() < 1 || depositRequest.getAmount() > 1000000) {
            throw new DepositNotValidException(depositRequest.getAmount() + "is not within the amount that can be deposited", 400);
        }
    }
    private String generateAccountNumber(){
        return String.valueOf(secureRandom.nextInt(1234556781));
    }
    private String generateAccountId(){
        return String.valueOf(++accountIdCounter);
    }
    private String generateTransactionId(){
        return String.valueOf(++transactionIdCounter);
    }
    private String formatDateToString(LocalDateTime date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, dd-MM-yyyy, hh-mm-ss, a");
        return dateTimeFormatter.format(date);
    }
    private void validateWithdrawal(WithdrawRequest withdrawRequest, Account account) {
        if(!account.getAccountPassword().equals(withdrawRequest.getPassword())){
            throw new IncorrectPasswordException("Password is incorrect", 400);
        }
        if(account.getAccountBalance().doubleValue() - withdrawRequest.getWithdrawAmount() < 500){
            throw new InsufficientBalanceException("You do not have sufficient balance",400);
        }
    }
}
