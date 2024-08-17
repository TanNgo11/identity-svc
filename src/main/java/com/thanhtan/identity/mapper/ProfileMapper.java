package com.thanhtan.identity.mapper;

import com.thanhtan.identity.dto.request.ProfileCreationRequest;
import com.thanhtan.identity.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}