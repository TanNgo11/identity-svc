package com.thanhtan.identity.controller;

import com.thanhtan.identity.dto.request.RoleRequest;
import com.thanhtan.identity.dto.response.ApiResponse;
import com.thanhtan.identity.dto.response.RoleResponse;
import com.thanhtan.identity.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.thanhtan.identity.constant.PathConstant.API_V1_ROLES;

@RestController
@RequestMapping(API_V1_ROLES)
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {


    IRoleService roleService;

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.success(roleService.getAllRoles());
    }

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {
        return ApiResponse.success(roleService.createRole(request));
    }

    @DeleteMapping({"/{roleId}"})
    public ApiResponse<Void> deleteRole(@PathVariable String roleId) {
        roleService.deleteRole(roleId);
        return ApiResponse.empty();
    }


}
