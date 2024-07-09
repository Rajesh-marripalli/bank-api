package com.miniproject.bank_payment.dto.request;

import lombok.Data;

@Data
public class TransferRequest {
    private Long sourceAccountId;
    private Long destinationAccountId;
    private Double amount;
}
