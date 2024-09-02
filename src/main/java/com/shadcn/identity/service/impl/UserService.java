package com.shadcn.identity.service.impl;

import com.shadcn.event.dto.NotificationEvent;
import com.shadcn.identity.dto.request.UserCreationRequest;
import com.shadcn.identity.dto.response.UserResponse;
import com.shadcn.identity.entity.User;
import com.shadcn.identity.enums.Role;
import com.shadcn.identity.enums.Status;
import com.shadcn.identity.exception.AppException;
import com.shadcn.identity.exception.ErrorCode;
import com.shadcn.identity.mapper.ProfileMapper;
import com.shadcn.identity.mapper.UserMapper;
import com.shadcn.identity.repository.RoleRepository;
import com.shadcn.identity.repository.UserRepository;
import com.shadcn.identity.repository.httpclient.ProfileClient;
import com.shadcn.identity.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder BCryptPasswordEncoder;
    ProfileMapper profileMapper;
    ProfileClient profileClient;
    KafkaTemplate<String, Object> kafkaTemplate;
    TemplateEngine templateEngine;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);

        if (user.getStatus() == Status.INACTIVE) throw new AppException(ErrorCode.USER_INACTIVE);

        user.setPassword(BCryptPasswordEncoder.encode(request.getPassword()));

        Set<com.shadcn.identity.entity.Role> roles = new HashSet<>();
        com.shadcn.identity.entity.Role userRole = roleRepository
                .findByName(Role.USER.name())
                .orElseGet(() -> {
                    log.info("USER role not found. Creating it.");
                    com.shadcn.identity.entity.Role role = new com.shadcn.identity.entity.Role();
                    role.setName(Role.USER.name());
                    role.setDescription("User role");
                    return roleRepository.save(role);
                });
        roles.add(userRole);
        user.setRoles(roles);
        user = userRepository.save(user);

        var profileCreationRequest = profileMapper.toProfileCreationRequest(request);
        profileCreationRequest.setUserId(String.valueOf(user.getId()));

        log.info("Creating profile: {}", profileCreationRequest);

        var profileResponse = profileClient.createProfile(profileCreationRequest);

        log.info("Profile created: {}", profileResponse);

        Context context = new Context();
        context.setVariable("name", request.getFirstName() + " " + request.getLastName());
        context.setVariable("email", request.getEmail());
        context.setVariable("verifyEmailLink", "http://localhost:8080/api/v1/users/verify-email?email=" + request.getEmail());

        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("EMAIL")
                .recipient(request.getEmail())
                .subject("Welcome to our system")
                .body(templateEngine.process("/template-email/index.html", context))
                .build();

        kafkaTemplate.send("notification-delivery", notificationEvent);

        return userMapper.toUserResponse(user);
    }

    // Generate 6 digit random otp
    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 900000 + 100000));
    }

    @Override
    public UserResponse getMyInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        UserResponse userResponse = userMapper.toUserResponse(user);
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(role.getName()));
        userResponse.setRoles(roles);
        return userResponse;
    }


    @Override
    public UserResponse verifyEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (user.isEmailVerified()) throw new AppException(ErrorCode.EMAIL_ALREADY_VERIFIED);
        else {
            user.setEmailVerified(true);
        }

        userRepository.save(user);

        // Send notification after email verified
        StringBuilder sb = new StringBuilder();
        sb.append("Hello, ").append(user.getUsername()).append(".\n")
                .append("Your email has been verified successfully.");

        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("EMAIL")
                .recipient(user.getEmail())
                .subject("Email Verified")
                .body(sb.toString())
                .build();

        kafkaTemplate.send("notification-delivery", notificationEvent);

        return userMapper.toUserResponse(user);
    }
}
