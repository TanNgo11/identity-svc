package com.shadcn.identity.service;

import com.shadcn.identity.dto.request.StudentCreationRequest;
import org.thymeleaf.context.Context;

public interface INotificationService {
    void sendVerifyEmail(StudentCreationRequest request, Context context);

    void sendVerifyEmailSuccess(String email);
}
