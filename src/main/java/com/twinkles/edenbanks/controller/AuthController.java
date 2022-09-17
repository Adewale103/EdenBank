package com.twinkles.edenbanks.controller;

import com.twinkles.edenbanks.data.model.User;
import com.twinkles.edenbanks.dtos.requests.LoginRequest;
import com.twinkles.edenbanks.dtos.requests.RegisterUserRequest;
import com.twinkles.edenbanks.dtos.responses.ApiResponse;
import com.twinkles.edenbanks.dtos.responses.LoginResponse;
import com.twinkles.edenbanks.security.jwt.TokenProvider;
import com.twinkles.edenbanks.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1/auth/")
public class AuthController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest registerUserRequest){
        ApiResponse apiResponse = userService.registerUserAccount(registerUserRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getAccountNumber(),loginRequest.getAccountPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateJWTToken(authentication);
        return new ResponseEntity<>(new LoginResponse(true,token), HttpStatus.OK);
    }
}
