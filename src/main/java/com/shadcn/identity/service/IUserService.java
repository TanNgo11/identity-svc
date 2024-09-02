package com.shadcn.identity.service;

import com.shadcn.identity.dto.request.UserCreationRequest;
import com.shadcn.identity.dto.response.UserResponse;

public interface IUserService {
    UserResponse createUser(UserCreationRequest request);

    UserResponse getMyInfo();

    UserResponse verifyEmail(String email);
}
