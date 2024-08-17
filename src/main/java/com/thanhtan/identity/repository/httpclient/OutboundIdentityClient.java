package com.thanhtan.identity.repository.httpclient;

import com.thanhtan.identity.dto.request.ExchangeTokenRequest;
import com.thanhtan.identity.dto.response.ExchangeTokenResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "outbound-identity", url = "https://oauth2.googleapis.com")
public interface OutboundIdentityClient {
        @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        ExchangeTokenResponse exchangeToken(@QueryMap ExchangeTokenRequest request);
}