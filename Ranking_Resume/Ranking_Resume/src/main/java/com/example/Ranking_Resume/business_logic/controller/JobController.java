package com.example.Ranking_Resume.business_logic.controller;

import com.example.Ranking_Resume.business_logic.dto.JobRequest;
import com.example.Ranking_Resume.business_logic.dto.JobResponse; // <-- Import this!
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.example.Ranking_Resume.business_logic.service.JobServiceImpl;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/business/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobServiceImpl jobService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JobResponse> createJobWithResumes(
            @RequestPart("job") JobRequest jobRequest,
            @RequestPart("resumes") MultipartFile[] resumes,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        JobResponse jobResponse = jobService.createJobWithResumes(jobRequest, resumes, userDetails.getUsername());
        return ResponseEntity.ok(jobResponse);
    }
}
