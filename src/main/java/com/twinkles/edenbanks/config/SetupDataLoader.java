package com.twinkles.edenbanks.config;

import com.twinkles.edenbanks.data.model.Account;
import com.twinkles.edenbanks.data.model.User;
import com.twinkles.edenbanks.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userRepository.findUserByAccountName("Admin") == null){
            User user = new User("Admin", "Admin", "adeyinkawale13@gmail.com",new Account("Admin Admin","1234567890",
                    BigDecimal.valueOf(1200),passwordEncoder.encode("1234")));
            userRepository.save(user);
        }
    }
}
