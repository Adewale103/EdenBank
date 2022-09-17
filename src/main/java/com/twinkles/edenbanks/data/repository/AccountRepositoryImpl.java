package com.twinkles.edenbanks.data.repository;

import com.twinkles.edenbanks.data.model.Account;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountRepositoryImpl implements AccountRepository{
    private final Map<String, Account> accounts = new HashMap<>();
    @Override
    public Account save(Account account) {
        if(accounts.containsKey(account.getAccountNumber())){
            accounts.replace(account.getAccountNumber(),account,account);
        }
        else{
            accounts.put(account.getAccountNumber(),account);}
        return account;
    }

    @Override
    public Account findAccountByAccountName(String accountName) {
       for(Map.Entry<String, Account> accountEntry : accounts.entrySet()){
           if(accountEntry.getValue().getAccountName().equals(accountName)){
               return accountEntry.getValue();
           }
       }
       return null;
    }

    @Override
    public Account findAccountByAccountNumber(String accountNumber) {
        if(accounts.containsKey(accountNumber)){
            return accounts.get(accountNumber);
        }
        return null;
    }

    @Override
    public void deleteAccount(Account account) {
        if(accounts.containsValue(account)){
            accounts.remove(account.getAccountNumber());
        }
    }

    @Override
    public int size() {
        return accounts.size();
    }
}
