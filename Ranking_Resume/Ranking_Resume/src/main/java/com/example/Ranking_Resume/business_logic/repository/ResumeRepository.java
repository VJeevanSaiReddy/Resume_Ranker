package com.example.Ranking_Resume.business_logic.repository;

import com.example.Ranking_Resume.business_logic.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByJobId(Long jobId);
}
