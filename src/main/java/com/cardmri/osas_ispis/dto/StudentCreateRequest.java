package com.cardmri.osas_ispis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCreateRequest {
    private String firstName;
    private String lastName;
    private String yearLevel;
    private Long userAccountId; // The ID of the UserAccount to link this profile to
    private Long programId;
}
