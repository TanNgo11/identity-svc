package com.shadcn.identity.controller;

import static com.shadcn.identity.constant.PathConstant.API_V1_AUTH;

import java.text.ParseException;

import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.JOSEException;
import com.shadcn.identity.dto.request.AuthenticationRequest;
import com.shadcn.identity.dto.request.IntrospectRequest;
import com.shadcn.identity.dto.request.LogoutRequest;
import com.shadcn.identity.dto.request.RefreshRequest;
import com.shadcn.identity.dto.response.ApiResponse;
import com.shadcn.identity.dto.response.AuthenticationResponse;
import com.shadcn.identity.dto.response.IntrospectResponse;
import com.shadcn.identity.service.IAuthenticationService;

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
