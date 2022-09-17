package com.twinkles.edenbanks.data.repository;

import com.twinkles.edenbanks.data.model.Account;
import com.twinkles.edenbanks.data.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Map<String, User> users = new HashMap<>();
    @Override
    public User save(User user) {
        if(users.containsKey(user.getId())){
            users.replace(user.getId(), users.get(user.getId()),user);
        }
        else{
            users.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public User findUserByAccount(Account account) {

        for(Map.Entry<String, User> userEntry : users.entrySet()){
            if(userEntry.getValue().getAccounts().contains(account)){
                return userEntry.getValue();
            }
        }
        return null;
    }

    @Override
    public void deleteUser(User user) {
        if(users.containsValue(user)){
            users.remove(user.getId());
        }
    }

    @Override
    public int size() {
        return users.size();
    }
}
