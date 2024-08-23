package com.thanhtan.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.thanhtan.identity.dto.request.RoleRequest;
import com.thanhtan.identity.dto.response.RoleResponse;
import com.thanhtan.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
