package com.shadcn.identity.service;

import com.shadcn.identity.dto.request.StudentCreationRequest;
import com.shadcn.identity.dto.response.UserResponse;

public interface IUserService {
    UserResponse createStudent(StudentCreationRequest request);

    UserResponse createTeacher(StudentCreationRequest request);

    UserResponse createAdmin(StudentCreationRequest request);

    UserResponse getMyInfo();

    UserResponse verifyEmail(String email);
}
