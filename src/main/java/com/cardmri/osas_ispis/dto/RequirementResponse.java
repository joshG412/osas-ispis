package com.cardmri.osas_ispis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequirementResponse {
    private Long id;
    private String title;
    private String type;
    private String academicYear;
}
