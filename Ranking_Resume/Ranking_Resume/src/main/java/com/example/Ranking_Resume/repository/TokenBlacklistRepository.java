package com.example.Ranking_Resume.repository;

import com.example.Ranking_Resume.entity.TokenBlacklist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, String> {
    boolean existsByToken(String token);

    @Transactional
    @Modifying
    @Query("DELETE FROM TokenBlacklist t WHERE t.expirationDate < ?1")
    void deleteByExpirationDateBefore(Date currentDate);
}
