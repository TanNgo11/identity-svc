package com.shadcn.identity.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.shadcn.identity.dto.request.AuthenticationRequest;
import com.shadcn.identity.dto.request.IntrospectRequest;
import com.shadcn.identity.dto.request.LogoutRequest;
import com.shadcn.identity.dto.request.RefreshRequest;
import com.shadcn.identity.dto.response.AuthenticationResponse;
import com.shadcn.identity.dto.response.IntrospectResponse;
import com.shadcn.identity.entity.User;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    String generateToken(User user, boolean isRefreshToken);

    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;




    //    AuthenticationResponse OutboundAuthenticate(String code);

}
