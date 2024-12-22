package com.shadcn.identity.service.impl;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.transaction.*;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.core.context.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
import com.shadcn.identity.util.excel.ExcelUtils;
import com.shadcn.identity.util.excel.FileFactory;
import com.shadcn.identity.util.excel.ImportConfig;

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
    RoleMapper roleMapper;
    ResetPasswordTokenRepository resetPasswordTokenRepository;
    RoleRepository roleRepository;
    PasswordEncoder BCryptPasswordEncoder;
    ProfileMapper profileMapper;
    ProfileClient profileClient;
    INotificationService notificationService;
    CourseClient deparmentsClient;

    @Override
    @Transactional
    public void createStudent(StudentCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        if (userRepository.findByEmail(request.getEmail()).isPresent()) throw new AppException(ErrorCode.EMAIL_EXISTED);
        User user = userMapper.toStudent(request);
        user = prepareAndSaveUser(user, request.getPassword(), com.shadcn.identity.enums.Role.STUDENT.toString());

        ProfileCreationRequest profileCreationRequest = profileMapper.toStudentProfileCreationRequest(request);
        profileCreationRequest.setUserId(String.valueOf(user.getId()));

        profileClient.createStudentProfile(profileCreationRequest);
        notificationService.sendVerifyEmail(
                request.getEmail(),
                userEmailVerificationContext(request.getFirstName(), request.getLastName(), request.getEmail()));
    }

    @Override
    @Transactional
    public void createTeacher(TeacherCreationRequest teacherCreationRequest) {
        if (userRepository.existsByUsername(teacherCreationRequest.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        if (userRepository.findByEmail(teacherCreationRequest.getEmail()).isPresent())
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        User user = userMapper.toTeacher(teacherCreationRequest);
        user = prepareAndSaveUser(
                user, teacherCreationRequest.getPassword(), com.shadcn.identity.enums.Role.TEACHER.toString());

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
    }

    @Override
    @Transactional
    public void createAdmin(AdminCreationRequest request) {
        // TODO create admin
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        if (userRepository.findByEmail(request.getEmail()).isPresent()) throw new AppException(ErrorCode.EMAIL_EXISTED);
        User user = userMapper.toAdmin(request);
        user = prepareAndSaveUser(user, request.getPassword(), request.getRole().toString());
        ProfileCreationRequest profileCreationRequest = profileMapper.toAdminProfileCreationRequest(request);
        profileCreationRequest.setUserId(String.valueOf(user.getId()));
        profileClient.createAdminProfile(profileCreationRequest);
        notificationService.sendVerifyEmail(
                request.getEmail(),
                userEmailVerificationContext(request.getFirstName(), request.getLastName(), request.getEmail()));
    }

    private User prepareAndSaveUser(User user, String password, String roleName) {
        if (user.getStatus() == Status.INACTIVE) throw new AppException(ErrorCode.USER_INACTIVE);
        user.setPassword(BCryptPasswordEncoder.encode(password));
        Set<Role> roles = new HashSet<>();
        Role userRole =
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
        User user = userRepository
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
    }

    @Override
    public UserResponse verifyEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));

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

    @Override
    public void changeListUserStatus(StatusUpdateRequest request) {
        List<Long> ids = request.getIds();
        ids.stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(user -> {
                    user.setStatus(request.getStatus());
                    userRepository.save(user);
                });
    }

    @Override
    public UserProfileResponse getUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Get user info: {}", username);

        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

        UserProfileResponse userProfileResponse =
                switch (roleNames.iterator().next()) {
                    case "STUDENT" -> profileClient.getStudentProfile(username).getResult();
                    case "TEACHER" -> profileClient.getTeacherProfile(username).getResult();
                    case "ADMIN" -> profileClient.getAdminProfile(username).getResult();

                    default -> throw new IllegalStateException(
                            "Unexpected value: " + roleNames.iterator().next());
                };

        userProfileResponse.setRoles(roleNames);
        return userProfileResponse;
    }

    @Override
    @Transactional
    public void importStudentDataFromExcel(MultipartFile importFile) {
        Workbook workbook = FileFactory.getWorkbookStream(importFile);

        List<StudentCreationRequest> studentRequests = ExcelUtils.getImportData(workbook, ImportConfig.studentImport);
        List<DepartmentResponse> departmentCodes =
                deparmentsClient.getAllDepartments().getResult();
        List<AcademicYearResponse> academicYears =
                deparmentsClient.getAllAcademicYears().getResult();

        for (StudentCreationRequest request : studentRequests) {
            String departmentCode = findDepartmentCode(departmentCodes, request.getDepartmentId());
            int academicYear = findAcademicYear(academicYears);

            String username =
                    generateUniqueUsername(request.getFirstName(), request.getLastName(), departmentCode, academicYear);
            String email = username + "@eiu.edu.vn";
            String tempPassword = generateTempPassword();

            request.setUsername(username);
            request.setEmail(email);
            request.setPassword(tempPassword);

            createStudent(request);
        }
    }

    @Override
    public void deleteTeachers(List<String> teacherIds) {
        profileClient.deleteTeacherProfile(teacherIds);
    }

    @Override
    public void deleteTeacherById(String teacherId) {
        userRepository.deleteById(Long.parseLong(teacherId));
        profileClient.deleteTeacherProfile(List.of(teacherId));
    }

    @Override
    public List<ExcelStudentResponse> getAllStudentProfilesByAcademicYearId(Long academicYearId) {
        // List<StudentProfileResponse> studentProfiles =
        // profileClient.getAllStudentByAcademicYearId(academicYearId).getResult();
        //
        // List<String> usernames =
        // studentProfiles.stream().map(StudentProfileResponse::getUsername).collect(Collectors.toList());
        //
        // List<User> users = userRepository.findAllByUsernameIn(usernames);
        //
        // List<ExcelStudentResponse> result =
        // userMapper.toExcelStudentResponseList(studentProfiles);
        //
        // Map<String, String> usernameToPasswordMap = users.stream()
        // .collect(Collectors.toMap(User::getUsername, User::getPassword));
        //
        // result.forEach(excelStudentResponse ->
        //
        // excelStudentResponse.setPassword(usernameToPasswordMap.get(excelStudentResponse.getUsername())));

        return null;
    }

    @Override
    public void deleteStudents(DeleteStudentRequest request) {
        List<String> missingUsers = new ArrayList<>();

        for(String username : request.getStudentUsernames()) {
            userRepository.findByUsername(username).ifPresentOrElse( userRepository::delete,
                    () -> missingUsers.add(username)
            );
        }

        if (!missingUsers.isEmpty()) {
            log.warn("Student profiles not found for IDs: {}", missingUsers);
        }

        profileClient.deleteStudents(request.getStudentUsernames());
    }

    private String findDepartmentCode(List<DepartmentResponse> departmentCodes, Long departmentId) {
        return departmentCodes.stream()
                .filter(department -> department.getId().equals(departmentId))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED))
                .getDepartmentCode();
    }

    private int findAcademicYear(List<AcademicYearResponse> academicYears) {
        return academicYears.stream()
                .filter(year -> year.getStartYear().isBefore(LocalDate.now())
                        && year.getEndYear().isAfter(LocalDate.now()))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND))
                .getStartYear()
                .getYear();
    }

    private String generateUniqueUsername(String firstName, String lastName, String departmentCode, int academicYear) {
        String baseUsername =
                firstName.toLowerCase() + "." + lastName.toLowerCase() + "." + departmentCode + academicYear;
        String username = baseUsername;
        int counter = 1;
        while (userRepository.existsByUsername(username)) {
            username = baseUsername + counter;
            counter++;
        }
        return username;
    }

    private String generateTempPassword() {

        int length = 8;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder tempPassword = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            tempPassword.append(chars.charAt(random.nextInt(chars.length())));
        }
        return tempPassword.toString();
    }
}
