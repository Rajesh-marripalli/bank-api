package com.miniproject.bank_payment.dto.response;

import lombok.Data;

@Data
public class AccountCreationResponse {
    private String message;


    public AccountCreationResponse(String message, String accountNumber) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}


