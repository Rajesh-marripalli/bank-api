package com.miniproject.bank_payment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;

    private String password;

    private String email;

    private Long phoneNumber;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Accounts> accounts;

    private String roles;

    // Getters and Setters
    //construtor
    //allargsconstrutor
}



