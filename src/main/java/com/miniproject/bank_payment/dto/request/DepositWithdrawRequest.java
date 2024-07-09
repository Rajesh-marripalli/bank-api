package com.miniproject.bank_payment.dto.request;

import lombok.Data;

@Data
public class DepositWithdrawRequest {
    private Long accountId;
    private Double amount;
}
