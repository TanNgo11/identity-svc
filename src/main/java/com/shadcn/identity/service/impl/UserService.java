package com.shadcn.identity.service.impl;

import java.time.*;
import java.util.*;

import jakarta.transaction.*;

import org.springframework.security.core.context.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.thymeleaf.context.*;

import com.shadcn.identity.dto.request.*;
import com.shadcn.identity.dto.response.*;
import com.shadcn.identity.entity.*;
import com.shadcn.identity.enums.Status;
import com.shadcn.identity.exception.*;
import com.shadcn.identity.mapper.*;
import com.shadcn.identity.repository.*;
import com.shadcn.identity.repository.httpclient.*;
import com.shadcn.identity.service.*;

import lombok.*;
import lombok.experimental.*;
import lombok.extern.slf4j.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {

    UserRepository userRepository;
    UserMapper userMapper;
    ResetPasswordTokenRepository resetPasswordTokenRepository;
    RoleRepository roleRepository;
    PasswordEncoder BCryptPasswordEncoder;
    ProfileMapper profileMapper;
    ProfileClient profileClient;
    INotificationService notificationService;

    @Override
    @Transactional
    public UserResponse createStudent(StudentCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toStudent(request);
        user = prepareAndSaveUser(user, request.getPassword(), request.getRole().toString());

        var profileCreationRequest = profileMapper.toStudentProfileCreationRequest(request);
        profileCreationRequest.setUserId(String.valueOf(user.getId()));

        profileClient.createStudentProfile(profileCreationRequest);
        notificationService.sendVerifyEmail(
                request.getEmail(),
                userEmailVerificationContext(request.getFirstName(), request.getLastName(), request.getEmail()));

        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse createTeacher(TeacherCreationRequest teacherCreationRequest) {
        if (userRepository.existsByUsername(teacherCreationRequest.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toTeacher(teacherCreationRequest);
        user = prepareAndSaveUser(
                user,
                teacherCreationRequest.getPassword(),
                teacherCreationRequest.getRole().toString());
        ProfileCreationRequest profileCreationRequest =
                profileMapper.toTeacherProfileCreationRequest(teacherCreationRequest);
        profileCreationRequest.setUserId(String.valueOf(user.getId()));
        profileClient.createTeacherProfile(profileCreationRequest);
        notificationService.sendVerifyEmail(
                teacherCreationRequest.getEmail(),
                userEmailVerificationContext(
                        teacherCreationRequest.getFirstName(),
                        teacherCreationRequest.getLastName(),
                        teacherCreationRequest.getEmail()));
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse createAdmin(AdminCreationRequest request) {
        // TODO create admin
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toAdmin(request);
        user = prepareAndSaveUser(user, request.getPassword(), request.getRole().toString());
        ProfileCreationRequest profileCreationRequest = profileMapper.toAdminProfileCreationRequest(request);
        profileCreationRequest.setUserId(String.valueOf(user.getId()));
        notificationService.sendVerifyEmail(
                request.getEmail(),
                userEmailVerificationContext(request.getFirstName(), request.getLastName(), request.getEmail()));
        return userMapper.toUserResponse(user);
    }

    private User prepareAndSaveUser(User user, String password, String roleName) {
        if (user.getStatus() == Status.INACTIVE) throw new AppException(ErrorCode.USER_INACTIVE);
        user.setPassword(BCryptPasswordEncoder.encode(password));
        Set<com.shadcn.identity.entity.Role> roles = new HashSet<>();
        com.shadcn.identity.entity.Role userRole =
                roleRepository.findByName(roleName).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        roles.add(userRole);
        user.setRoles(roles);
        return userRepository.save(user);
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
    public void forgotPassword(UserForgotPasswordRequest request) {
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Long userId = user.getId();
        String email = user.getEmail();
        ResetPasswordToken existingToken = resetPasswordTokenRepository.findByUserId(userId);
        String token;

        if (existingToken != null) {
            // Reuse the existing token if it hasn't expired, or update it if it has
            if (existingToken.getExpiryDateTime().isBefore(LocalDateTime.now())) {
                token = UUID.randomUUID().toString();
                existingToken.setToken(token);
                existingToken.setExpiryDateTime(LocalDateTime.now().plusMinutes(30));
            } else {
                token = existingToken.getToken(); // Reuse the existing token
            }
            resetPasswordTokenRepository.save(existingToken); // Update the existing token
        } else {
            // If no token exists, create a new one
            token = UUID.randomUUID().toString();
            LocalDateTime expiryDateTime = LocalDateTime.now().plusMinutes(30);
            ResetPasswordToken resetPasswordToken = ResetPasswordToken.builder()
                    .token(token)
                    .expiryDateTime(expiryDateTime)
                    .user(user)
                    .build();
            resetPasswordTokenRepository.save(resetPasswordToken);
        }
        notificationService.sendResetPasswordEmail(email, resetPasswordContext(email, token));
    }

    @Override
    @Transactional
    public void resetPassword(UserResetPasswordRequest request) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(request.getToken());
        if (resetPasswordToken == null) {
            throw new AppException(ErrorCode.INVALID_OR_EXPIRED_TOKEN);
        }
        if (resetPasswordToken.getExpiryDateTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.INVALID_OR_EXPIRED_TOKEN);
        }
        User user = resetPasswordToken.getUser();
        user.setPassword(BCryptPasswordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        resetPasswordTokenRepository.delete(resetPasswordToken);
        System.out.println(resetPasswordToken);
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
        notificationService.sendVerifyEmailSuccess(email);

        return userMapper.toUserResponse(user);
    }

    public Context resetPasswordContext(String email, String token) {
        Context context = new Context();
        context.setVariable("email", email);
        context.setVariable("resetPasswordLink", "http://localhost:4567/loggin/resetpassword?token=" + token);
        return context;
    }

    public Context userEmailVerificationContext(String firstName, String lastName, String email) {

        Context context = new Context();
        context.setVariable("name", firstName + " " + lastName);
        context.setVariable("email", email);
        context.setVariable(
                "verifyEmailLink", "http://localhost:8080/identity/api/v1/users/verify-email?email=" + email);

        return context;
    }
}
