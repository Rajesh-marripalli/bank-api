package com.miniproject.bank_payment.dto.response;

import java.util.Date;

public class TransactionDetail {
    private String transactionType;
    private Double amount;
    private Date transactionDate;

    public TransactionDetail(String transactionType, Double amount, Date transactionDate) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
