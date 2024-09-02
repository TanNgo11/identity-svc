package com.shadcn.identity.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.shadcn.event.dto.NotificationEvent;
import com.shadcn.identity.dto.request.UserCreationRequest;
import com.shadcn.identity.service.INotificationService;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService implements INotificationService {

    KafkaTemplate<String, Object> kafkaTemplate;
    TemplateEngine templateEngine;

    @Override
    public void sendVerifyEmail(UserCreationRequest request, Context context) {
        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("EMAIL")
                .recipient(request.getEmail())
                .subject("Welcome to our system")
                .body(templateEngine.process("/template-email/index.html", context))
                .build();

        kafkaTemplate.send("notification-delivery", notificationEvent);
    }

    @Override
    public void sendVerifyEmailSuccess(String email) {
        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("EMAIL")
                .recipient(email)
                .subject("Welcome to our system")
                .body(templateEngine.process("/template-email-successfully/index.html", new Context()))
                .build();

        kafkaTemplate.send("notification-delivery", notificationEvent);
    }

}
