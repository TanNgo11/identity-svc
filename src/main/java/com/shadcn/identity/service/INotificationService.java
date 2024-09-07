package com.shadcn.identity.service;

import org.thymeleaf.context.Context;

public interface INotificationService {
    void sendVerifyEmail(String email, Context context);

    void sendVerifyEmailSuccess(String email);

    void sendResetPasswordEmail(String email, Context context);
}
