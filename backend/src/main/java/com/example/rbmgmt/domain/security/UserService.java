package com.example.rbmgmt.domain.security;

import com.example.rbmgmt.infra.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(String username, String email, String rawPassword) {
        User user = User.builder()
            .username(username)
            .email(email)
            .password(passwordEncoder.encode(rawPassword))
            .build();
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) { return userRepository.findByEmail(email); }
}


