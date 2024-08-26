package com.shadcn.identity.controller;

import static com.shadcn.identity.constant.PathConstant.API_V1_PERMISSION;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shadcn.identity.dto.request.PermissionRequest;
import com.shadcn.identity.dto.response.ApiResponse;
import com.shadcn.identity.dto.response.PermissionResponse;
import com.shadcn.identity.service.impl.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(API_V1_PERMISSION)
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @GetMapping
    ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.success(permissionService.getAllPermission());
    }

    @PostMapping
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ApiResponse.success(permissionService.createPermission(request));
    }

    @DeleteMapping("/{permissionId}")
    ApiResponse<Void> deletePermission(@PathVariable String permissionId) {
        permissionService.deletePermission(permissionId);
        return ApiResponse.empty();
    }
}
