package com.miniproject.bank_payment.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.mapping.ToOne;


import java.util.Set;


@Data
@Entity
@Table(name = "accounts")
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(unique = true, nullable = false)
    private String accountNumber;
    private Double balance;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Transactions> transactions;


    // Getters and Setters
}

