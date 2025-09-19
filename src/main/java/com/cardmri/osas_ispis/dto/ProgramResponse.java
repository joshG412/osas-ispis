package com.cardmri.osas_ispis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProgramResponse {
    private Long id;
    private String code;
    private String name;
}
