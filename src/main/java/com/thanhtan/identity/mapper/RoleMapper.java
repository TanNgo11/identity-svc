package com.thanhtan.identity.mapper;

import com.thanhtan.identity.dto.request.RoleRequest;
import com.thanhtan.identity.dto.response.RoleResponse;
import com.thanhtan.identity.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
