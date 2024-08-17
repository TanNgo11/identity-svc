package com.thanhtan.identity.mapper;


import com.thanhtan.identity.dto.request.CreateSystemUserRequest;
import com.thanhtan.identity.dto.request.SystemUserUpdationRequest;
import com.thanhtan.identity.dto.request.UserCreationRequest;
import com.thanhtan.identity.dto.request.UserUpdationRequest;
import com.thanhtan.identity.dto.response.UserResponse;
import com.thanhtan.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User toUser(UserCreationRequest request);

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
