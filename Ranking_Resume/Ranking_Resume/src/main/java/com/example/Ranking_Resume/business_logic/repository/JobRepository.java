package com.example.Ranking_Resume.business_logic.repository;

import com.example.Ranking_Resume.business_logic.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByUserId(Long userId);
}
