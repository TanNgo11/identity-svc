package com.thanhtan.identity.service;

import com.thanhtan.identity.dto.request.RoleRequest;
import com.thanhtan.identity.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse createRole(RoleRequest request);

    List<RoleResponse> getAllRoles();

    void deleteRole(String roleId);
}
