package com.shadcn.identity.repository.httpclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import com.shadcn.identity.config.AuthenticationRequestInterceptor;
import com.shadcn.identity.dto.response.AcademicYearResponse;
import com.shadcn.identity.dto.response.ApiResponse;
import com.shadcn.identity.dto.response.DepartmentResponse;
import com.shadcn.identity.exception.RetreiveMessageErrorDecoder;

@FeignClient(
        name = "course-svc",
        url = "${app.services.course}",
        configuration = {AuthenticationRequestInterceptor.class, RetreiveMessageErrorDecoder.class})
public interface CourseClient {
    @GetMapping(value = "/api/v1/departments", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<List<DepartmentResponse>> getAllDepartments();

    @GetMapping(value = "/api/v1/academic-years/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<List<AcademicYearResponse>> getAllAcademicYears();
}
