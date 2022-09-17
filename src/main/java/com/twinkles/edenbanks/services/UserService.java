package com.twinkles.edenbanks.services;

import com.twinkles.edenbanks.data.model.User;
import com.twinkles.edenbanks.dtos.requests.RegisterUserRequest;
import com.twinkles.edenbanks.dtos.responses.ApiResponse;

public interface UserService {
    ApiResponse registerUserAccount(RegisterUserRequest registerUserRequest);
    User findUserByAccountNumber(String accountNumber);

}
