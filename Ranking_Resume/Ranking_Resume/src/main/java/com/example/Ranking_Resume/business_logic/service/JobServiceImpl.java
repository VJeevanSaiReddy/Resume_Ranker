package com.example.Ranking_Resume.business_logic.service;


import com.example.Ranking_Resume.business_logic.dto.JobRequest;
import com.example.Ranking_Resume.business_logic.entity.Job;
import com.example.Ranking_Resume.business_logic.entity.Resume;
import com.example.Ranking_Resume.business_logic.repository.JobRepository;
import com.example.Ranking_Resume.business_logic.repository.ResumeRepository;
import com.example.Ranking_Resume.entity.User;
import com.example.Ranking_Resume.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl {
    private final JobRepository jobRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public Job createJobWithResumes(
        JobRequest jobRequest,
        MultipartFile[] resumes,
        String username
    ){
        User user=userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: "+ username));
        Job job = Job.builder()
                .title(jobRequest.getTitle())
                .description(jobRequest.getDescription())
                .user(user)
                .build();
        job = jobRepository.save(job);
        List<Resume> resumeList = new ArrayList<>();
        for (MultipartFile file : resumes) {
            try {
                Resume resume = Resume.builder()
                        .fileName(file.getOriginalFilename())
                        .data(file.getBytes())
                        .job(job)
                        .user(user)
                        .build();
                resumeList.add(resume);
            } catch (IOException e) {
                throw new RuntimeException("File processing failed: " + file.getOriginalFilename(), e);
            }
        }
        resumeRepository.saveAll(resumeList);
        job.setResumes(resumeList);

        return job;
    }
}
