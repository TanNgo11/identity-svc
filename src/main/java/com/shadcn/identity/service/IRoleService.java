package com.shadcn.identity.service;

import java.util.List;

import com.shadcn.identity.dto.request.RoleRequest;
import com.shadcn.identity.dto.response.RoleResponse;

public interface IRoleService {
    RoleResponse createRole(RoleRequest request);

    List<RoleResponse> getAllRoles();

    void deleteRole(String roleId);
}
