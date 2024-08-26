package com.shadcn.identity.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shadcn.event.dto.NotificationEvent;
import com.shadcn.identity.dto.request.UserCreationRequest;
import com.shadcn.identity.dto.response.UserResponse;
import com.shadcn.identity.entity.User;
import com.shadcn.identity.enums.Role;
import com.shadcn.identity.enums.Status;
import com.shadcn.identity.exception.AppException;
import com.shadcn.identity.exception.ErrorCode;
import com.shadcn.identity.mapper.ProfileMapper;
import com.shadcn.identity.mapper.UserMapper;
import com.shadcn.identity.repository.RoleRepository;
import com.shadcn.identity.repository.UserRepository;
import com.shadcn.identity.repository.httpclient.ProfileClient;
import com.shadcn.identity.service.IUserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder BCryptPasswordEncoder;
    ProfileMapper profileMapper;
    ProfileClient profileClient;
    KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);

        if (user.getStatus() == Status.INACTIVE) throw new AppException(ErrorCode.USER_INACTIVE);

        user.setPassword(BCryptPasswordEncoder.encode(request.getPassword()));

        Set<com.shadcn.identity.entity.Role> roles = new HashSet<>();
        com.shadcn.identity.entity.Role userRole = roleRepository
                .findByName(Role.USER.name())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        roles.add(userRole);
        user.setRoles(roles);
        user = userRepository.save(user);

        var profileCreationRequest = profileMapper.toProfileCreationRequest(request);
        profileCreationRequest.setUserId(user.getId().toString());

        profileClient.createProfile(profileCreationRequest);
        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("EMAIL")
                .recipient(request.getFirstName())
                .subject("Welcome to bookteria")
                .body("Hello, " + request.getUsername())
                .build();

        kafkaTemplate.send("notification-delivery", notificationEvent);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        UserResponse userResponse = userMapper.toUserResponse(user);
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(role.getName()));
        userResponse.setRoles(roles);
        return userResponse;
    }

    //    @Override
    //    public UserResponse updateSystemUser(UpdateSystemUserRequest request, MultipartFile file, Long userId) {
    //        User user = userRepository.findById(userId);
    //        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
    //        String oldImagePublicId = getPublicIdFromUrl(user.getAvatar() != null ? user.getAvatar() : "");
    //        userMapper.updateUser(user, request);
    //        if (file != null) {
    //            Map data = cloudinaryService.upload(file);
    //            if (data != null) {
    //                String newImageUrl = (String) data.get("url");
    //                if (!oldImagePublicId.isEmpty()) cloudinaryService.deleteImage(oldImagePublicId);
    //                user.setAvatar(newImageUrl);
    //            }
    //        }
    //        Set<com.thanhtan.identity.entity.Role> roles = new HashSet<>();
    //        com.thanhtan.identity.entity.Role userRole = roleRepository.findByName(request.getRoles())
    //                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    //        roles.add(userRole);
    //        user.setRoles(roles);
    //        User updatedUser = userRepository.save(user);
    //
    //        return userMapper.toUserResponse(updatedUser);
    //    }

    //    @Override
    //    public UserResponse createSystemUser(CreateSystemUserRequest request, MultipartFile file) {
    //        if (userRepository.existsByUsername(request.getUsername()))
    //            throw new AppException(ErrorCode.USER_EXISTED);
    //
    //        User user = userMapper.toUser(request);
    //
    //        if (user.getStatus() == Status.INACTIVE)
    //            throw new AppException(ErrorCode.USER_INACTIVE);
    //
    //        user.setPassword(BCryptPasswordEncoder.encode(request.getPassword()));
    //
    //        Set<com.thanhtan.identity.entity.Role> roles = new HashSet<>();
    //        com.thanhtan.identity.entity.Role userRole = roleRepository.findByName(request.getRoles())
    //                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    //        roles.add(userRole);
    //        user.setRoles(roles);
    //
    //        if (file != null) {
    //            Map data = cloudinaryService.upload(file);
    //            if (data != null) {
    //                String imageUrl = (String) data.get("url");
    //                user.setAvatar(imageUrl);
    //            }
    //        }
    //
    //        return userMapper.toUserResponse(userRepository.save(user));
    //    }
    //
    //    @Override
    //    public UserResponse updateUserProfileById(UpdateUserRequest request, MultipartFile file, Long userId) {
    //        User user = userRepository.findById(userId);
    //        if (user == null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
    //        String oldImagePublicId = getPublicIdFromUrl(user.getAvatar() != null ? user.getAvatar() : "");
    //        userMapper.updateUser(user, request);
    //        if (file != null) {
    //            Map data = cloudinaryService.upload(file);
    //            if (data != null) {
    //                String newImageUrl = (String) data.get("url");
    //                if (!oldImagePublicId.isEmpty()) cloudinaryService.deleteImage(oldImagePublicId);
    //                user.setAvatar(newImageUrl);
    //            }
    //        }
    ////       user.setStatus(request.getStatus());
    //        User updatedUser = userRepository.save(user);
    //
    //        return userMapper.toUserResponse(updatedUser);
    //    }
    //
    //    @Override
    //    public UserResponse updateUserProfile(UpdateUserRequest request, MultipartFile file) {
    //        String username = SecurityContextHolder.getContext().getAuthentication().getName();
    //        User user = userRepository.findByUsername(username).orElseThrow(() -> new
    // AppException(ErrorCode.USER_NOT_EXISTED));
    //        String oldImagePublicId = getPublicIdFromUrl(user.getAvatar() != null ? user.getAvatar() : "");
    //        userMapper.updateUser(user, request);
    //        if (file != null) {
    //            Map data = cloudinaryService.upload(file);
    //            if (data != null) {
    //                String newImageUrl = (String) data.get("url");
    //                if (!oldImagePublicId.isEmpty()) cloudinaryService.deleteImage(oldImagePublicId);
    //                user.setAvatar(newImageUrl);
    //            }
    //        }
    //        User updatedUser = userRepository.save(user);
    //
    //        return userMapper.toUserResponse(updatedUser);
    //    }

    @Override
    public List<UserResponse> getUsers() {

        return userMapper.toUserResponseList(userRepository.findAllWithRoles());
    }

    @Override
    public UserResponse getSystemUsersById(Long userId) {
        User user = userRepository.findById(userId);
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(role.getName()));

        if (!roles.contains("ADMIN") && !roles.contains("STAFF")) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setRoles(roles);
        return userResponse;
    }

    @Override
    public UserResponse getCustomerById(Long userId) {
        User user = userRepository.findById(userId);
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(role.getName()));

        if (!roles.contains("USER")) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setRoles(roles);
        return userResponse;
    }

    @Override
    public void deleteUserssByIds(Long[] ids) {
        for (Long id : ids) {
            User user = userRepository.findById(id);
            if (user != null) {
                user.setStatus(Status.INACTIVE);
                userRepository.save(user);
            }
        }
    }

    @Override
    public List<UserResponse> getAllAdmin() {
        List<User> adminUsers = userRepository.findAllWithRoles(Role.ADMIN.name());
        List<User> staffUsers = userRepository.findAllWithRoles(Role.STAFF.name());

        List<User> combinedUsers = new ArrayList<>();
        combinedUsers.addAll(adminUsers);
        combinedUsers.addAll(staffUsers);

        return userMapper.toUserResponseList(combinedUsers);
    }

    @Override
    public List<UserResponse> getAllCustomer() {
        return userMapper.toUserResponseList(userRepository.findAllWithRoles(Role.USER.name()));
    }
}