package com.twinkles.edenbanks.data.repository;

import com.twinkles.edenbanks.data.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootApplication
class AccountRepositoryTest {

    @Autowired
    private final AccountRepository accountRepository = new AccountRepositoryImpl();
    private Account account1;
    private Account account2;


    @BeforeEach
    void setUp() {
        account1 = new Account("Adewale Adeyinka","1234567890", BigDecimal.valueOf(2300),"1234");
        account2 = new Account("Tomisin Funmi","98765567890", BigDecimal.valueOf(5200),"1234");
    }

    @Test
    public void saveAccountTest(){
        accountRepository.save(account1);
        assertEquals(1,accountRepository.size());
    }
    @Test
    public void findAccountByAccountNumberTest(){
        accountRepository.save(account1);
        accountRepository.save(account2);
        Account foundAccount = accountRepository.findAccountByAccountNumber("1234567890");
        assertEquals("Adewale Adeyinka", foundAccount.getAccountName());
    }
    @Test
    public void findAccountByAccountNameTest(){
        accountRepository.save(account1);
        accountRepository.save(account2);
        Account foundAccount = accountRepository.findAccountByAccountName("Tomisin Funmi");
        assertEquals("98765567890", foundAccount.getAccountNumber());
    }

    @Test
    public void deleteAccountTest(){
        accountRepository.save(account1);
        accountRepository.save(account2);
        accountRepository.deleteAccount(account2);
        assertEquals(1, accountRepository.size());
    }
}