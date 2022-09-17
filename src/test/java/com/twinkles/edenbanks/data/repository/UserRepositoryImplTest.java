package com.twinkles.edenbanks.data.repository;


import com.twinkles.edenbanks.data.model.Account;
import com.twinkles.edenbanks.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserRepositoryImplTest {
    @Autowired
    private final UserRepository userRepository = new UserRepositoryImpl();
    private Account account1;
    private Account account2;
    private User user1;



    @BeforeEach
    void setUp() {
        user1 = new User("Adewale", "Adeyinka", "Adeyinka@gmmail.com", "09082367383");
        account1 = new Account(user1.getFirstName()+" "+user1.getLastName(),"1234567890", BigDecimal.valueOf(2300),"1234");
        account2 = new Account(user1.getFirstName()+" "+user1.getLastName(),"98765567890", BigDecimal.valueOf(5200),"1234");
        user1.getAccounts().add(account1);
        user1.getAccounts().add(account2);
        userRepository.save(user1);
    }

    @Test
    public void createUserTest(){
        assertEquals(1, userRepository.size());
        assertEquals(2,user1.getAccounts().size());
    }

    @Test
    public void findUserByAccountTest(){
        User user = userRepository.findUserByAccount(account1);
        assertEquals(user, user1);
    }

    @Test
    public void deleteUserTest(){
        userRepository.deleteUser(user1);
        assertEquals(0, userRepository.size());
    }


}