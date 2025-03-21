package com.unir.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserDetails user;
    private Date expiration;

    public LoginResponse(String accessToken, String refreshToken, UserDetails user, Date expiration) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
        this.expiration = expiration;
    }

    // Constructor para manejar errores
    public LoginResponse(String message) {
        this.accessToken = message;
        this.refreshToken = null;
        this.user = null;
        this.expiration = null;
    }
}