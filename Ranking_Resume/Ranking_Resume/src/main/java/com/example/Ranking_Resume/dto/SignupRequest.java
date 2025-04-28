package com.example.Ranking_Resume.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
