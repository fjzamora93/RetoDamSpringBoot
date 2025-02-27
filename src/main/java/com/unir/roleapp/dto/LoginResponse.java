package com.unir.roleapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String email;
    private String token;
    private Date expiration;


    public LoginResponse(String message) {
        this.email = null;
        this.token = message;
        this.expiration = null;
    }

}
