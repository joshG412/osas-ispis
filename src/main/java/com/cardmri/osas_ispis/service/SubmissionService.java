package com.cardmri.osas_ispis.service;

import com.cardmri.osas_ispis.dto.SubmissionCreateRequest;
import com.cardmri.osas_ispis.dto.SubmissionResponse;
import com.cardmri.osas_ispis.dto.SubmissionStatusUpdateRequest;
import com.cardmri.osas_ispis.entity.Requirement;
import com.cardmri.osas_ispis.entity.Student;
import com.cardmri.osas_ispis.entity.Submission;
import com.cardmri.osas_ispis.repository.RequirementRepository;
import com.cardmri.osas_ispis.repository.StudentRepository;
import com.cardmri.osas_ispis.repository.SubmissionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final StudentRepository studentRepository; // We'll need this soon
    private final RequirementRepository requirementRepository;

    // For a student to create a submission
    public SubmissionResponse createSubmission(SubmissionCreateRequest request, Student student) {
        Requirement requirement = requirementRepository.findById(request.getRequirementId())
                .orElseThrow(() -> new EntityNotFoundException("Requirement not found"));

        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setRequirement(requirement);
        submission.setStatus("Pending"); // Default status
        submission.setDateSubmitted(LocalDate.now());

        Submission savedSubmission = submissionRepository.save(submission);
        return mapToSubmissionResponse(savedSubmission);
    }

    // For admins/staff to update status
    public SubmissionResponse updateSubmissionStatus(Long id, SubmissionStatusUpdateRequest request) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Submission not found"));

        submission.setStatus(request.getStatus());
        Submission updatedSubmission = submissionRepository.save(submission);
        return mapToSubmissionResponse(updatedSubmission);
    }
    
    // For admins/staff to see all submissions
    public List<SubmissionResponse> getAllSubmissions() {
        return submissionRepository.findAll().stream()
                .map(this::mapToSubmissionResponse)
                .collect(Collectors.toList());
    }

    private SubmissionResponse mapToSubmissionResponse(Submission submission) {
        return SubmissionResponse.builder()
                .id(submission.getId())
                .status(submission.getStatus())
                .dateSubmitted(submission.getDateSubmitted())
                .studentId(submission.getStudent().getId())
                .studentName(submission.getStudent().getFirstName() + " " + submission.getStudent().getLastName())
                .requirementId(submission.getRequirement().getId())
                .requirementTitle(submission.getRequirement().getTitle())
                .build();
    }
}
