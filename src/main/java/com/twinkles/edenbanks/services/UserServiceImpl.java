package com.twinkles.edenbanks.services;

import com.twinkles.edenbanks.data.model.Account;
import com.twinkles.edenbanks.data.model.User;
import com.twinkles.edenbanks.data.model.enums.RoleType;
import com.twinkles.edenbanks.data.repository.UserRepository;
import com.twinkles.edenbanks.dtos.requests.CreateAccountRequest;
import com.twinkles.edenbanks.dtos.requests.RegisterUserRequest;
import com.twinkles.edenbanks.dtos.responses.ApiResponse;
import com.twinkles.edenbanks.exceptions.UserAlreadyExistException;
import com.twinkles.edenbanks.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private static int userIdCounter;
    private final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, AccountService accountService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Override
    public ApiResponse registerUserAccount(RegisterUserRequest registerUserRequest) {
        validateThatUserDoesNotExist(registerUserRequest.getEmail());
        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .accountName(registerUserRequest.getFirstName()+" "+registerUserRequest.getLastName())
                .accountPassword(bCryptPasswordEncoder.encode(registerUserRequest.getAccountPassword()))
                .InitialDeposit(registerUserRequest.getInitialDeposit())
                .build();
        accountService.createAccount(createAccountRequest);
        Account account = accountService.findAccountByAccountName(createAccountRequest.getAccountName());
        User user = new User(registerUserRequest.getFirstName(), registerUserRequest.getLastName(), registerUserRequest.getEmail(), account);
        user.setId(generateUserId());
        userRepository.save(user);
        log.info(user.getAccount().getAccountNumber());
        return ApiResponse.builder()
                .successful(true)
                .message("User successfully registered!")
                .statusCode(200)
                .build();

    }

    @Override
    public User findUserByAccountNumber(String accountNumber) {
        User user = userRepository.findUserByAccountNumber(accountNumber);
        validateThatUserExist(accountNumber);
        return user;
    }


    private String generateUserId() {
        return String.valueOf(++userIdCounter);
    }

    private void validateThatUserDoesNotExist(String email) {
       User foundUser =  userRepository.findUserByEmailAddress(email);
       if(foundUser != null){
           throw new UserAlreadyExistException("User "+foundUser.getFirstName()+" already exist",400);
       }
    }

    private void validateThatUserExist(String accountNumber) {
        User user = userRepository.findUserByAccountNumber(accountNumber);
        if(user == null){
            throw new UserNotFoundException("User with account number: "+accountNumber+" does not exist",404);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByAccountNumber(username);
        if(user != null){
            return new org.springframework.security.core.userdetails.User(user.getAccount().getAccountNumber(), user.getAccount().getAccountPassword(),getAuthorities(user.getRoles()));

        }
        return null;

    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<RoleType> roles) {
       return roles.stream().map(role-> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }
}
