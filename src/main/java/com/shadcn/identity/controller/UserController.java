package com.shadcn.identity.controller;

import static com.shadcn.identity.constant.PathConstant.*;

import jakarta.validation.*;

import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import com.shadcn.identity.dto.request.*;
import com.shadcn.identity.dto.response.*;
import com.shadcn.identity.service.*;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.security.*;
import lombok.*;
import lombok.experimental.*;
import lombok.extern.slf4j.*;

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

    @PatchMapping("/status/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> changeStatus(@PathVariable String username, @RequestBody StatusUpdateRequest request) {
        userService.changeUserStatus(username, request);
        return ApiResponse.empty();
    }

    @GetMapping("/myInfo/{username}")
    @PreAuthorize("hasRole('STUDENT')||hasRole('ADMIN')||hasRole('TEACHER')")
    public ApiResponse<UserProfileResponse> getUserInfo(@PathVariable String username) {
        return ApiResponse.success(userService.getUserInfo(username));
    }
}
