package com.shadcn.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.shadcn.identity.dto.request.RoleRequest;
import com.shadcn.identity.dto.response.RoleResponse;
import com.shadcn.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
