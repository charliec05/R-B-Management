package com.example.rbmgmt.config;

import com.example.rbmgmt.domain.security.Role;
import com.example.rbmgmt.domain.security.User;
import com.example.rbmgmt.infra.repos.RoleRepository;
import com.example.rbmgmt.infra.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        roleRepository.findByName("ADMIN").orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").build()));
        roleRepository.findByName("USER").orElseGet(() -> roleRepository.save(Role.builder().name("USER").build()));

        var maybeAdmin = userRepository.findByUsername("admin").or(() -> userRepository.findByEmail("admin@example.com"));
        if (maybeAdmin.isEmpty()) {
            var adminRole = roleRepository.findByName("ADMIN").orElseThrow();
            User admin = User.builder()
                .username("admin")
                .email("admin@example.com")
                .password(passwordEncoder.encode("admin123"))
                .build();
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
        } else {
            User admin = maybeAdmin.get();
            if (admin.getEmail() == null) {
                admin.setEmail("admin@example.com");
            }
            // ensure ADMIN role
            var adminRole = roleRepository.findByName("ADMIN").orElseThrow();
            admin.getRoles().add(adminRole);
            // ensure password usable for quickstart
            admin.setPassword(passwordEncoder.encode("admin123"));
            userRepository.save(admin);
        }
    }
}


