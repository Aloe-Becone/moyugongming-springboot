package com.shiroha.userauthenticator.requests;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class AuthRequest {
    String phoneNumber;
    String password;
    String code;
}
