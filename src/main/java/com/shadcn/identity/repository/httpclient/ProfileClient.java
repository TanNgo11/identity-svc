package com.shadcn.identity.repository.httpclient;

import com.shadcn.identity.dto.response.*;
import com.shadcn.identity.exception.RetreiveMessageErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.shadcn.identity.config.AuthenticationRequestInterceptor;
import com.shadcn.identity.dto.request.ProfileCreationRequest;

@FeignClient(
        name = "profile-service",
        url = "${app.services.profile}",
        configuration = {AuthenticationRequestInterceptor.class, RetreiveMessageErrorDecoder.class})
public interface ProfileClient {
    @PostMapping(value = "/api/v1/users/student", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        ApiResponse<Void> createStudentProfile(@ModelAttribute ProfileCreationRequest request);

    @PostMapping(value = "/api/v1/users/teacher", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ApiResponse<Void> createTeacherProfile(@RequestBody ProfileCreationRequest request);

    @PostMapping(value = "/api/v1/users/admin", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ApiResponse<Void> createAdminProfile(@RequestBody ProfileCreationRequest request);

    @GetMapping(value = "/api/v1/users/student/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<StudentProfileResponse> getStudentProfile(@PathVariable String username);

    @GetMapping(value = "/api/v1/users/teacher/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<TeacherProfileResponse> getTeacherProfile(@PathVariable String username);

    @GetMapping(value = "/api/v1/users/admin/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<AdminProfileResponse> getAdminProfile(@PathVariable String username);

}
