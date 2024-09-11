package com.granzotti.financial_control.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ResetPasswordRequest {
    private String email;
}
