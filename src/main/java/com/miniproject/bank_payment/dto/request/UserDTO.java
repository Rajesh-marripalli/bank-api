package com.miniproject.bank_payment.dto.request;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String roles;
}
