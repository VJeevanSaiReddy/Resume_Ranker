package com.example.Ranking_Resume.service;

import com.example.Ranking_Resume.dto.SignupRequest;
import com.example.Ranking_Resume.repository.UserRepository;
import com.example.Ranking_Resume.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Ranking_Resume.dto.LoginRequest;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(SignupRequest signupRequest){
        if(userRepository.existsByUsername(signupRequest.getUsername())){
            throw new RuntimeException("Username is already taken");
        }
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            throw new RuntimeException("Email is already registered");
        }
        if(!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())){
            throw new RuntimeException("Passwords does not match");
        }

        String activationCode = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .activationCode(activationCode)
                .enabled(false)
                .role("RECRUITER")
                .build();
        userRepository.save(user);
        emailService.sendActivationEmail(user.getEmail(), activationCode);
    }
    public boolean activateAccount(String code) {
        Optional<User> userOpt = userRepository.findByActivationCode(code);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEnabled(true);
            user.setActivationCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    public String login(LoginRequest request) {
        String loginId = request.getLoginId();
        String actualUsername = userRepository.findByEmail(loginId)
                .map(User::getUsername)
                .orElse(loginId);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(actualUsername, request.getPassword())
        );
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(actualUsername);
        return jwtService.generateToken(userDetails);
    }
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
