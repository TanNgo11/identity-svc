package com.shadcn.identity.service;

import com.shadcn.identity.dto.request.*;
import com.shadcn.identity.dto.response.UserProfileResponse;
import com.shadcn.identity.dto.response.UserResponse;

public interface IUserService {
    void createStudent(StudentCreationRequest request);

    void createTeacher(TeacherCreationRequest request);

    void createAdmin(AdminCreationRequest request);

    UserResponse getMyInfo();

    void forgotPassword(UserForgotPasswordRequest request);

    void resetPassword(UserResetPasswordRequest request);

    UserResponse verifyEmail(String email);

    void changeUserStatus(String username, StatusUpdateRequest request);

    UserProfileResponse getUserInfo(String username);
}
    