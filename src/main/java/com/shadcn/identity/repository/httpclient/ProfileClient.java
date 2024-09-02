package com.shadcn.identity.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shadcn.identity.config.AuthenticationRequestInterceptor;
import com.shadcn.identity.dto.request.ProfileCreationRequest;
import com.shadcn.identity.dto.response.ApiResponse;
import com.shadcn.identity.dto.response.UserProfileResponse;

@FeignClient(
        name = "profile-service",
        url = "${app.services.profile}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface ProfileClient {
    @PostMapping(value = "/api/v1/users/student", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createStudentProfile(@RequestBody ProfileCreationRequest request);

    @PostMapping(value = "/api/v1/users/teacher", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createTeacherProfile(@RequestBody ProfileCreationRequest request);

    @PostMapping(value = "/api/v1/users/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createAdminProfile(@RequestBody ProfileCreationRequest request);
}
