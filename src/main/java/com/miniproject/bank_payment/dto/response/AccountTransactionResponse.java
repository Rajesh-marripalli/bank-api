package com.miniproject.bank_payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionResponse {
    private String accountNumber;
    private Double currentBalance;
    private List<TransactionDetail> transactions;

}
