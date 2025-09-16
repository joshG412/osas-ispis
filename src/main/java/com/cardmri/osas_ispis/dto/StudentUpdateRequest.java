package com.cardmri.osas_ispis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentUpdateRequest {
    private String firstName;
    private String lastName;
    private String yearLevel;
}
