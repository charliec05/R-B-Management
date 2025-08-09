package com.example.rbmgmt.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDtos {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private String token;
    }
}


