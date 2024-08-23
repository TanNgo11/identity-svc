package com.thanhtan.identity.service;

import java.util.List;

import com.thanhtan.identity.dto.request.PermissionRequest;
import com.thanhtan.identity.dto.response.PermissionResponse;

public interface IPermissionService {
    PermissionResponse createPermission(PermissionRequest request);

    List<PermissionResponse> getAllPermission();

    void deletePermission(String id);
}
