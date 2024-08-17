package com.thanhtan.identity.service;

import com.nimbusds.jose.JOSEException;
import com.thanhtan.identity.dto.request.AuthenticationRequest;
import com.thanhtan.identity.dto.request.IntrospectRequest;
import com.thanhtan.identity.dto.request.LogoutRequest;
import com.thanhtan.identity.dto.request.RefreshRequest;
import com.thanhtan.identity.dto.response.AuthenticationResponse;
import com.thanhtan.identity.dto.response.IntrospectResponse;
import com.thanhtan.identity.entity.User;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    String generateToken(User user, boolean isRefreshToken);

    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

//    AuthenticationResponse OutboundAuthenticate(String code);


}
