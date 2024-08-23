package com.thanhtan.identity.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.thanhtan.identity.dto.request.RoleRequest;
import com.thanhtan.identity.dto.response.RoleResponse;
import com.thanhtan.identity.entity.Role;
import com.thanhtan.identity.exception.AppException;
import com.thanhtan.identity.exception.ErrorCode;
import com.thanhtan.identity.mapper.RoleMapper;
import com.thanhtan.identity.repository.PermissionRepository;
import com.thanhtan.identity.repository.RoleRepository;
import com.thanhtan.identity.service.IRoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService implements IRoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        Role role = roleMapper.toRole(request);

        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.RESOURCE_EXISTED);
        }

        role.setPermissions(new HashSet<>(permissionRepository.findAllById(request.getPermissions())));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteRole(String roleId) {
        roleRepository.deleteById(roleId);
    }
}
