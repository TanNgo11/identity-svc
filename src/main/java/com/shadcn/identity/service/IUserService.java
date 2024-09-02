package com.shadcn.identity.service;

import com.shadcn.identity.dto.request.AdminCreationRequest;
import com.shadcn.identity.dto.request.StudentCreationRequest;
import com.shadcn.identity.dto.request.TeacherCreationRequest;
import com.shadcn.identity.dto.response.UserResponse;

public interface IUserService {
    UserResponse createStudent(StudentCreationRequest request);

    UserResponse createTeacher(TeacherCreationRequest request);

    UserResponse createAdmin(AdminCreationRequest request);

    UserResponse getMyInfo();

    UserResponse verifyEmail(String email);
}
