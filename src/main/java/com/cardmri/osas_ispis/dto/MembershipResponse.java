package com.cardmri.osas_ispis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MembershipResponse {
    private Long id;
    private String position;
    private Long studentId;
    private String studentName;
    private Long organizationId;
    private String organizationName;
}
