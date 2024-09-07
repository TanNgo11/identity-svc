package com.shadcn.identity.mapper;

import org.mapstruct.Mapper;

import com.shadcn.identity.dto.request.*;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toStudentProfileCreationRequest(StudentCreationRequest request);

    ProfileCreationRequest toTeacherProfileCreationRequest(TeacherCreationRequest request);

    ProfileCreationRequest toAdminProfileCreationRequest(AdminCreationRequest request);
}
