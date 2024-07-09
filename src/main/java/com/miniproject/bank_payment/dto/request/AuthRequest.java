package com.miniproject.bank_payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    public String username;
    private String password;
    //TODO Security implemention handling the username and password
}
