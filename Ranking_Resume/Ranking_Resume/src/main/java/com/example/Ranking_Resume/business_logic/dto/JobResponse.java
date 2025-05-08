package com.example.Ranking_Resume.business_logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JobResponse {
    private Long id;
    private String title;
    private String description;
    private List<String> resumeFileNames;
}

