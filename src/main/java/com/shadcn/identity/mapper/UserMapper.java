package com.shadcn.identity.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.shadcn.identity.dto.request.*;
import com.shadcn.identity.dto.response.UserResponse;
import com.shadcn.identity.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toStudent(StudentCreationRequest request);

    User toTeacher(TeacherCreationRequest request);

    User toAdmin(AdminCreationRequest request);

    @Mapping(target = "roles", source = "roles", ignore = true)
    User toStudent(CreateSystemUserRequest request);

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
