package com.example.Ranking_Resume.repository;

import com.example.Ranking_Resume.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByActivationCode(String activationCode);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
