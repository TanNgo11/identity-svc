package com.thanhtan.identity.mapper;

import org.mapstruct.Mapper;

import com.thanhtan.identity.dto.request.ProfileCreationRequest;
import com.thanhtan.identity.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
