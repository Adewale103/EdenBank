package com.twinkles.edenbanks.data.repository;

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
    public User findUserByAccountName(String accountName) {

        for(Map.Entry<String, User> userEntry : users.entrySet()){
            if(userEntry.getValue().getAccount().getAccountName().equals(accountName)){
                return userEntry.getValue();
            }
        }
        return null;
    }

    @Override
    public User findUserByAccountNumber(String accountNumber) {
        for(Map.Entry<String, User> userEntry : users.entrySet()){
            if(userEntry.getValue().getAccount().getAccountNumber().equals(accountNumber)){
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
    public User findUserByEmailAddress(String emailAddress) {
        for(Map.Entry<String, User> entryMap : users.entrySet()){
            if(entryMap.getValue().getEmail().equals(emailAddress)){
                return entryMap.getValue();
            }
        }
        return null;
    }

    @Override
    public int size() {
        return users.size();
    }
}
