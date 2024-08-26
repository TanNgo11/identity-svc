package com.shadcn.identity.service;

import java.util.List;

import com.shadcn.identity.dto.request.PermissionRequest;
import com.shadcn.identity.dto.response.PermissionResponse;

public interface IPermissionService {
    PermissionResponse createPermission(PermissionRequest request);

    List<PermissionResponse> getAllPermission();

    void deletePermission(String id);
}
