package com.twinkles.edenbanks.data.repository;

import com.twinkles.edenbanks.data.model.User;


public interface UserRepository {
    User save(User user);
    User findUserByAccountName(String accountName);
    User findUserByAccountNumber(String accountNumber);
    void deleteUser(User user);
    User findUserByEmailAddress(String emailAddress);
    int size();
}
