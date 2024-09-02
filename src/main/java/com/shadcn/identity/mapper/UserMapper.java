package com.shadcn.identity.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.shadcn.identity.dto.request.CreateSystemUserRequest;
import com.shadcn.identity.dto.request.SystemUserUpdationRequest;
import com.shadcn.identity.dto.request.StudentCreationRequest;
import com.shadcn.identity.dto.request.UserUpdationRequest;
import com.shadcn.identity.dto.response.UserResponse;
import com.shadcn.identity.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(StudentCreationRequest request);

    @Mapping(target = "roles", source = "roles", ignore = true)
    User toUser(CreateSystemUserRequest request);

    @Mapping(target = "roles", source = "roles", ignore = true)
    UserResponse toUserResponse(User user);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdationRequest request);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, SystemUserUpdationRequest request);

    List<UserResponse> toUserResponseList(List<User> allUsers);
}
