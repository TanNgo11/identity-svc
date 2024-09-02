package com.shadcn.identity.controller;

import static com.shadcn.identity.constant.PathConstant.API_V1_USERS;

import com.shadcn.identity.dto.request.AdminCreationRequest;
import com.shadcn.identity.dto.request.StudentCreationRequest;
import com.shadcn.identity.dto.request.TeacherCreationRequest;
import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.shadcn.identity.dto.response.ApiResponse;
import com.shadcn.identity.dto.response.UserResponse;
import com.shadcn.identity.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(API_V1_USERS)
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    IUserService userService;

    @PostMapping("/student/registration")
    ApiResponse<UserResponse> createStudent(@RequestBody @Valid StudentCreationRequest request) {
        return ApiResponse.success(userService.createStudent(request));
    }

    @PostMapping("/teacher/registration")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<UserResponse> createTeacher(@RequestBody @Valid TeacherCreationRequest request) {
        return ApiResponse.success(userService.createTeacher(request));
    }

    @PostMapping("/admin/registration")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<UserResponse> createAdmin(@RequestBody @Valid AdminCreationRequest request) {
        return ApiResponse.success(userService.createAdmin(request));
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/myInfo")
    @PreAuthorize("hasRole('STUDENT')||hasRole('ADMIN')||hasRole('TEACHER')")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.success(userService.getMyInfo());
    }

    @GetMapping("/verify-email")
    public ApiResponse<UserResponse> verifyEmail(@RequestParam("email") String email) {
        log.info("Verify email: {}", email);
        return ApiResponse.success(userService.verifyEmail(email));
    }
}
