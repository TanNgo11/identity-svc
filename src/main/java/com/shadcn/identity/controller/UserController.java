package com.shadcn.identity.controller;

import static com.shadcn.identity.constant.PathConstant.API_V1_USERS;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.shadcn.identity.dto.request.*;
import com.shadcn.identity.dto.response.ApiResponse;
import com.shadcn.identity.dto.response.UserProfileResponse;
import com.shadcn.identity.dto.response.UserResponse;
import com.shadcn.identity.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@RestController
@RequestMapping(API_V1_USERS)
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    IUserService userService;

    @PostMapping("/student/registration")
    ApiResponse<Void> createStudent(@RequestBody @Valid StudentCreationRequest request) {
        userService.createStudent(request);
        return ApiResponse.empty();
    }

    @PostMapping("/teacher/registration")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<Void> createTeacher(@RequestBody @Valid TeacherCreationRequest request) {
        userService.createTeacher(request);
        return ApiResponse.empty();
    }

    @PostMapping("/admin/registration")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<Void> createAdmin(@RequestBody @Valid AdminCreationRequest request) {
        userService.createAdmin(request);
        return ApiResponse.empty();
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/myInfo")
    @PreAuthorize("hasRole('STUDENT')||hasRole('ADMIN')||hasRole('TEACHER')")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.success(userService.getMyInfo());
    }

    @GetMapping("/verify-email")
    public ApiResponse<UserResponse> verifyEmail(@RequestParam("email") String email) {
        //        log.info("Verify email: {}", email);
        return ApiResponse.success(userService.verifyEmail(email));
    }

    @PostMapping("/forgot-password")
    public ApiResponse<Void> resetPassword(@RequestBody @Valid UserForgotPasswordRequest request) {
        userService.forgotPassword(request);
        return ApiResponse.empty();
    }

    @PostMapping("/reset-password")
    public ApiResponse<Void> updatePassword(@RequestBody @Valid UserResetPasswordRequest request) {
        userService.resetPassword(request);
        return ApiResponse.empty();
    }

    @PatchMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> changeUserStatusByIds(@RequestBody StatusUpdateRequest request) {
        userService.changeListUserStatus(request);
        return ApiResponse.empty();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')||hasRole('ADMIN')||hasRole('TEACHER')")
    public ApiResponse<UserProfileResponse> getUserInfo() {
        return ApiResponse.success(userService.getUserInfo());
    }

    @PostMapping("/students/import")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> importCustomerData(@RequestParam("file") MultipartFile importFile) {
        userService.importStudentDataFromExcel(importFile);
        return ApiResponse.empty();
    }

    @DeleteMapping("/teachers/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteTeacher(@RequestBody DeleteTeacherRequest request) {
        log.info("Delete teacher: {}", request.getTeacherIds());
        userService.deleteTeachers(request.getTeacherIds());
        return ApiResponse.empty();
    }

    @DeleteMapping("/teachers/delete/{teacherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteTeacherById(@PathVariable String teacherId) {
        log.info("Delete teacher by id: {}", teacherId);
        userService.deleteTeacherById(teacherId);
        return ApiResponse.empty();
    }
}
