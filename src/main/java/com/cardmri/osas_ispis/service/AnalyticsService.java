package com.cardmri.osas_ispis.service;

import com.cardmri.osas_ispis.repository.StudentRepository;
import com.cardmri.osas_ispis.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final StudentRepository studentRepository;
    private final SubmissionRepository submissionRepository;

    public Map<String, Long> getDashboardKpis() {
        long totalStudents = studentRepository.count();
        long pendingRequirements = submissionRepository.countByStatus("Pending");
        long totalScholars = submissionRepository.countByRequirementType("Scholarship");

        return Map.of(
            "totalStudents", totalStudents,
            "pendingRequirements", pendingRequirements,
            "totalScholars", totalScholars
        );
    }
}
