package com.twinkles.edenbanks.services;

import com.twinkles.edenbanks.dtos.requests.LoginRequest;
import com.twinkles.edenbanks.dtos.responses.LoginResponse;
import com.twinkles.edenbanks.dtos.requests.RegisterUserRequest;
import com.twinkles.edenbanks.dtos.responses.ApiResponse;

public interface UserService {
    ApiResponse registerUserAccount(RegisterUserRequest registerUserRequest);
    LoginResponse Login(LoginRequest loginRequest);

}
