package com.shadcn.identity.service;

import java.text.ParseException;
import java.util.List;

import com.nimbusds.jose.JOSEException;
import com.shadcn.identity.dto.request.UserCreationRequest;
import com.shadcn.identity.dto.response.UserResponse;

public interface IUserService {
    UserResponse createUser(UserCreationRequest request);

    UserResponse getMyInfo();

    //    UserResponse updateUserProfile(UpdateUserRequest request, MultipartFile file);
    //
    //    UserResponse createSystemUser(CreateSystemUserRequest request, MultipartFile file);
    //
    //    UserResponse updateSystemUser(UpdateSystemUserRequest request, MultipartFile file, Long userId);
    //
    //    UserResponse updateUserProfileById(UpdateUserRequest request, MultipartFile file, Long userId);

    List<UserResponse> getUsers();

    List<UserResponse> getAllCustomer();

    UserResponse getCustomerById(Long userId);

    UserResponse getSystemUsersById(Long userId);

    List<UserResponse> getAllAdmin();

    void deleteUserssByIds(Long[] ids);

    // Verify Email using Link
    UserResponse verifyEmail(String email);
}
