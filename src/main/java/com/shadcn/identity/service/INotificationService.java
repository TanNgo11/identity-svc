package com.shadcn.identity.service;

import com.shadcn.identity.dto.request.UserCreationRequest;
import org.thymeleaf.context.Context;

public interface INotificationService {
    void sendVerifyEmail(UserCreationRequest request, Context context);

    void sendVerifyEmailSuccess(String email);
}
