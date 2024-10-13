package com.shadcn.identity.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shadcn.identity.entity.ExceptionMessage;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class RetreiveMessageErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ExceptionMessage.class);
        } catch (IOException e) {
            log.error("Failed to decode error message", e);
            return new RuntimeException("Failed to process response body", e);
        }
        int errorCode = message.getCode();
        String errorMessage = message.getMessage() != null ? message.getMessage() : "Error occurred";

        return switch (response.status()) {
            case 400 -> new BadRequestException(errorMessage,errorCode);
            case 404 -> new NotFoundException(errorMessage);
            default -> errorDecoder.decode(methodKey, response);
        };
    }
}

