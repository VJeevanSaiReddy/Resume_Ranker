package com.example.Ranking_Resume.service;

import com.example.Ranking_Resume.repository.TokenBlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenCleanupService {
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Scheduled(fixedRate = 86400000)
    public void cleanupExpiredTokens() {
        tokenBlacklistRepository.deleteByExpirationDateBefore(new Date());
    }
}
