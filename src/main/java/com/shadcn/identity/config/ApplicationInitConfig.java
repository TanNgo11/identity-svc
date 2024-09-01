package com.shadcn.identity.config;

import static java.rmi.server.LogStream.log;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shadcn.identity.entity.User;
import com.shadcn.identity.repository.RoleRepository;
import com.shadcn.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                // Set default role for admin user
                // If the role is not existed, create it

                HashSet<com.shadcn.identity.entity.Role> roles = new HashSet<>();
                roles.add(roleRepository.findByName("ADMIN").orElseGet(() -> {
                    log("ADMIN role not found. Creating it.");
                    com.shadcn.identity.entity.Role role = new com.shadcn.identity.entity.Role();
                    role.setName("ADMIN");
                    return roleRepository.save(role);
                }));

                // orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_EXISTED))
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                ApplicationInitConfig.log.warn(
                        "admin user has been created with default password: admin, please change it");
            }
            //            emailSender.sendTestEmail();
        };
    }
}
