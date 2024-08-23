package com.thanhtan.identity.controller;

import static com.thanhtan.identity.constant.PathConstant.API_V1_AUTH;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.thanhtan.identity.dto.request.AuthenticationRequest;
import com.thanhtan.identity.dto.request.IntrospectRequest;
import com.thanhtan.identity.dto.request.LogoutRequest;
import com.thanhtan.identity.dto.request.RefreshRequest;
import com.thanhtan.identity.dto.response.ApiResponse;
import com.thanhtan.identity.dto.response.AuthenticationResponse;
import com.thanhtan.identity.dto.response.IntrospectResponse;
import com.thanhtan.identity.service.IAuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(API_V1_AUTH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    IAuthenticationService authenticationService;

    //    @PostMapping("/outbound/authenticate")
    //    ApiResponse<AuthenticationResponse> authenticateOutbound(@RequestParam("code") String code) {
    //        return ApiResponse.success(authenticationService.OutboundAuthenticate(code));
    //    }

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.success(result);
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws JOSEException, ParseException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}
