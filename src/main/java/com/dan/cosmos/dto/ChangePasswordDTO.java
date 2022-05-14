package com.dan.cosmos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordDTO {
    private String token;
    private String password;
    private String passwordConfirm;
}
