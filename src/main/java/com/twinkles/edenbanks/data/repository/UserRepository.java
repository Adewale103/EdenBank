package com.twinkles.edenbanks.data.repository;

import com.twinkles.edenbanks.data.model.Account;
import com.twinkles.edenbanks.data.model.User;


public interface UserRepository {
    User save(User user);
    User findUserByAccount(Account account);
    void deleteUser(User user);
    int size();
}
