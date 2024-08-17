package com.thanhtan.identity.service;

import com.thanhtan.identity.dto.request.UserCreationRequest;
import com.thanhtan.identity.dto.response.UserResponse;

import java.util.List;

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

}
