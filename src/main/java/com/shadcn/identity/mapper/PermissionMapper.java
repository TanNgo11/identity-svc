package com.shadcn.identity.mapper;

import org.mapstruct.Mapper;

import com.shadcn.identity.dto.request.PermissionRequest;
import com.shadcn.identity.dto.response.PermissionResponse;
import com.shadcn.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
