package com.thanhtan.identity.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.thanhtan.identity.dto.request.PermissionRequest;
import com.thanhtan.identity.dto.response.PermissionResponse;
import com.thanhtan.identity.entity.Permission;
import com.thanhtan.identity.exception.AppException;
import com.thanhtan.identity.exception.ErrorCode;
import com.thanhtan.identity.mapper.PermissionMapper;
import com.thanhtan.identity.repository.PermissionRepository;
import com.thanhtan.identity.service.IPermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService implements IPermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    @Transactional
    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        if (permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.RESOURCE_EXISTED);
        }
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    @Override
    public List<PermissionResponse> getAllPermission() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).collect(Collectors.toList());
    }

    @Override
    public void deletePermission(String id) {
        permissionRepository.deleteById(id);
    }
}
