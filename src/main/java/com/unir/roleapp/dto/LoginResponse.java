package com.unir.roleapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private UserDetails user;
    private Date expiration;


    public LoginResponse(String message) {
        this.token = message;
        this.user = null;
        this.expiration = null;
    }

}
