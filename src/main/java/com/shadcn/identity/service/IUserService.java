package com.shadcn.identity.service;

import com.shadcn.identity.dto.request.*;
import com.shadcn.identity.dto.response.UserResponse;

public interface IUserService {
    UserResponse createStudent(StudentCreationRequest request);

    UserResponse createTeacher(TeacherCreationRequest request);

    UserResponse createAdmin(AdminCreationRequest request);

    UserResponse getMyInfo();

    void forgotPassword(UserForgotPasswordRequest request);

    void resetPassword(UserResetPasswordRequest request);

    UserResponse verifyEmail(String email);
}
