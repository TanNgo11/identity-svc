package com.shadcn.identity.service;

import com.nimbusds.jose.JOSEException;
import com.shadcn.identity.dto.request.*;
import com.shadcn.identity.dto.response.AuthenticationResponse;
import com.shadcn.identity.dto.response.IntrospectResponse;
import com.shadcn.identity.entity.User;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    String generateToken(User user, boolean isRefreshToken);

    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

    String changePassword(ChangePasswordRequest request);

    AuthenticationResponse OutboundAuthenticate(String code);

}
