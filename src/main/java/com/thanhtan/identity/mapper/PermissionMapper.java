package com.thanhtan.identity.mapper;

import org.mapstruct.Mapper;

import com.thanhtan.identity.dto.request.PermissionRequest;
import com.thanhtan.identity.dto.response.PermissionResponse;
import com.thanhtan.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
