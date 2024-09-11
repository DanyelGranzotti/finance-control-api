package com.granzotti.financial_control.dto;

import lombok.*;

@Setter
@Getter
@Data
public class AuthRequest {

    private String username;
    private String email;
    private String password;


}
