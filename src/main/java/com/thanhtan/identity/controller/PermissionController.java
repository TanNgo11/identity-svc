package com.thanhtan.identity.controller;


import com.thanhtan.identity.dto.request.PermissionRequest;
import com.thanhtan.identity.dto.response.ApiResponse;
import com.thanhtan.identity.dto.response.PermissionResponse;
import com.thanhtan.identity.service.impl.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.thanhtan.identity.constant.PathConstant.API_V1_PERMISSION;

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
