package com.thanhtan.identity.mapper;

import com.thanhtan.identity.dto.request.PermissionRequest;
import com.thanhtan.identity.dto.response.PermissionResponse;
import com.thanhtan.identity.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
