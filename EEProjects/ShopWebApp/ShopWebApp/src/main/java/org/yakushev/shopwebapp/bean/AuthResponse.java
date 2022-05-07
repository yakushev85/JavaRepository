package org.yakushev.shopwebapp.bean;

import lombok.Data;

@Data
public class AuthResponse {
    private Long id;
    private String username;
    private String token;
}
