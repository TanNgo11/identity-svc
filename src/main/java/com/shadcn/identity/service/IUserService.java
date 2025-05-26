package com.shadcn.identity.service;

import java.util.List;

import com.shadcn.identity.dto.response.*;
import org.springframework.web.multipart.MultipartFile;

import com.shadcn.identity.dto.request.*;

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

    UserProfileResponse getStudentProfileById(Long userId);

    List<UserProfileResponse> getListUserProfilesByIds(List<Long> userIds);

    PageResponse<TeacherProfileResponse> getListTeachers(int current, int pageSize, String departmentId);

    UserProfileResponse getUserProfileById(Long userId);

    void importStudentDataFromExcel(MultipartFile importFile);

    void deleteTeachers(List<String> teacherIds);

    void deleteTeacherById(String teacherId);

    List<ExcelStudentResponse> getAllStudentProfilesByAcademicYearId(Long academicYearId);

    void deleteStudents(DeleteStudentRequest request);

    UserResponse getUserDetailByUsername(String username);

    void updateFaceVerified(UpdateFaceVerifyRequest request);
}
