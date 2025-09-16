package com.cardmri.osas_ispis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String yearLevel;
    private String email; // Include the user's email for context
}
