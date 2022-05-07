package org.yakushev.shopwebapp.bean;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
