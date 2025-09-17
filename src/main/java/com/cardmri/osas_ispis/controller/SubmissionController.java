package com.cardmri.osas_ispis.controller;

import com.cardmri.osas_ispis.dto.SubmissionCreateRequest;
import com.cardmri.osas_ispis.dto.SubmissionResponse;
import com.cardmri.osas_ispis.dto.SubmissionStatusUpdateRequest;
import com.cardmri.osas_ispis.entity.Student;
import com.cardmri.osas_ispis.entity.UserAccount;
import com.cardmri.osas_ispis.repository.StudentRepository;
import com.cardmri.osas_ispis.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;
    private final StudentRepository studentRepository;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<SubmissionResponse> createSubmission(
            @RequestBody SubmissionCreateRequest request,
            @AuthenticationPrincipal UserAccount user // Injects the currently logged-in user
    ) {
         Student student = studentRepository.findByUserAccountId(user.getId())
                .orElseThrow(() -> new RuntimeException("Student profile not found for user"));

        SubmissionResponse response = submissionService.createSubmission(request, student);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissions() {
        return ResponseEntity.ok(submissionService.getAllSubmissions());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<SubmissionResponse> updateSubmissionStatus(
            @PathVariable Long id,
            @RequestBody SubmissionStatusUpdateRequest request
    ) {
        return ResponseEntity.ok(submissionService.updateSubmissionStatus(id, request));
    }
    // ... other code in SubmissionController

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<SubmissionResponse>> getMySubmissions(
            @AuthenticationPrincipal UserAccount user
    ) {
        // Find the linked Student profile for the authenticated UserAccount
        Student student = studentRepository.findByUserAccountId(user.getId())
                .orElseThrow(() -> new RuntimeException("Student profile not found for user"));

        return ResponseEntity.ok(submissionService.getSubmissionsByStudent(student));
    }

}
