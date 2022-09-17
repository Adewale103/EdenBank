package com.twinkles.edenbanks.data.repository;

import com.twinkles.edenbanks.data.model.Account;


public interface AccountRepository {
    Account save(Account account);
    Account findAccountByAccountName(String accountName);
    Account findAccountByAccountNumber(String accountNumber);
    void deleteAccount(Account account);
    int size();

}
