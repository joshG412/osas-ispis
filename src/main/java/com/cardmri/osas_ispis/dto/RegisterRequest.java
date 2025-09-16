package com.cardmri.osas_ispis.dto;

import com.cardmri.osas_ispis.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private Role role;
}
