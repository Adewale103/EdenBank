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
        account1 = new Account("Adewale Adeyinka","1234567890", BigDecimal.valueOf(2300),"1234");
        user1 = new User("Adewale", "Adeyinka", "Adeyinka@gmmail.com",account1);
        userRepository.save(user1);

    }

    @Test
    public void createUserTest(){
        assertEquals(1, userRepository.size());
    }

    @Test
    public void findUserByAccountNameTest(){
        User user = userRepository.findUserByAccountName("Adewale Adeyinka");
        assertEquals(user, user1);
    }

    @Test
    public void deleteUserTest(){
        userRepository.deleteUser(user1);
        assertEquals(0, userRepository.size());
    }


}