package com.cardmri.osas_ispis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembershipRequest {
    private Long studentId;
    private Long organizationId;
    private String position;
}
