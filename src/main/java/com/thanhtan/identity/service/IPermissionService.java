package com.thanhtan.identity.service;

import com.thanhtan.identity.dto.request.PermissionRequest;
import com.thanhtan.identity.dto.response.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse createPermission(PermissionRequest request);

    List<PermissionResponse> getAllPermission();

    void deletePermission(String id);
}
