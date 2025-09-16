package com.cardmri.osas_ispis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class SubmissionResponse {
    private Long id;
    private String status;
    private LocalDate dateSubmitted;
    private Long studentId;
    private String studentName;
    private Long requirementId;
    private String requirementTitle;
}
