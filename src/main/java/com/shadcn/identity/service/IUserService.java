package com.shadcn.identity.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shadcn.identity.dto.request.*;
import com.shadcn.identity.dto.response.ExcelStudentResponse;
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

    void changeListUserStatus(StatusUpdateRequest request);

    UserProfileResponse getUserInfo();

    void importStudentDataFromExcel(MultipartFile importFile);

    void deleteTeachers(List<String> teacherIds);

    void deleteTeacherById(String teacherId);

    List<ExcelStudentResponse> getAllStudentProfilesByAcademicYearId(Long academicYearId);
}
