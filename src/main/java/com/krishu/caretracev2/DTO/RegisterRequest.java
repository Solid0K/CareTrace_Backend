package com.krishu.caretracev2.DTO;

import com.krishu.caretracev2.ClientRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
