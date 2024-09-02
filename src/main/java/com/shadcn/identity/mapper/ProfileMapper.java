package com.shadcn.identity.mapper;

import org.mapstruct.Mapper;

import com.shadcn.identity.dto.request.ProfileCreationRequest;
import com.shadcn.identity.dto.request.StudentCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(StudentCreationRequest request);
}
