package com.example.Ranking_Resume.controller;

import com.example.Ranking_Resume.dto.JwtResponse;
import com.example.Ranking_Resume.dto.SignupRequest;
import com.example.Ranking_Resume.dto.LoginRequest;
import com.example.Ranking_Resume.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody SignupRequest signupRequest){
        authService.register(signupRequest);
        return ResponseEntity.ok("Registration successful! Please check your email to activate your account.");
    }
    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam String code) {
        boolean activated = authService.activateAccount(code);
        if (activated) {
            return ResponseEntity.ok("Account activated successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired activation code.");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: No user logged in");
        }
        try {
            authService.deleteUser(userDetails.getUsername());
            return ResponseEntity.ok("Account deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-unactivated")
    public ResponseEntity<String> deleteUnactivatedUser(@RequestParam String activation_code) {
        boolean deleted = authService.deleteUnactivatedUserByActivationCode(activation_code);
        if (deleted) {
            return ResponseEntity.ok("Unactivated account deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Invalid or already activated code");
        }
    }

}
