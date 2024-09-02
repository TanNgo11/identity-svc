package com.shadcn.identity.controller;

import static com.shadcn.identity.constant.PathConstant.API_V1_USERS;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.shadcn.identity.dto.request.UserCreationRequest;
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
    ApiResponse<UserResponse> createStudent(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.success(userService.createUser(request));
    }

    @PostMapping("/teacher/registration")
    ApiResponse<UserResponse> createTeacher(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.success(userService.createUser(request));
    }

    @PostMapping("/admin/registration")
    ApiResponse<UserResponse> createAdmin(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.success(userService.createUser(request));
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/myInfo")
    @PreAuthorize("hasRole('USER')||hasRole('ADMIN')||hasRole('STAFF')")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.success(userService.getMyInfo());
    }

    @GetMapping("/verify-email")
    public ApiResponse<UserResponse> verifyEmail(@RequestParam("email") String email) {
        log.info("Verify email: {}", email);
        return ApiResponse.success(userService.verifyEmail(email));
    }
}
