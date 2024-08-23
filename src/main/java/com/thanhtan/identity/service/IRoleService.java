package com.thanhtan.identity.service;

import java.util.List;

import com.thanhtan.identity.dto.request.RoleRequest;
import com.thanhtan.identity.dto.response.RoleResponse;

public interface IRoleService {
    RoleResponse createRole(RoleRequest request);

    List<RoleResponse> getAllRoles();

    void deleteRole(String roleId);
}
