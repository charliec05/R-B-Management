package com.example.rbmgmt.api.controllers;

import com.example.rbmgmt.api.dto.AuthDtos;
import com.example.rbmgmt.config.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthDtos.AuthResponse> login(@Valid @RequestBody AuthDtos.LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new AuthDtos.AuthResponse(token));
    }
}


