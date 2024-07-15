package com.miniproject.bank_payment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts account;

    private String transactionType;

    private Double amount;

    private Date transactionDate;

}

