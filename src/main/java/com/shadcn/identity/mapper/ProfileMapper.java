package com.shadcn.identity.mapper;

import org.mapstruct.Mapper;

import com.shadcn.identity.dto.request.ProfileCreationRequest;
import com.shadcn.identity.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
