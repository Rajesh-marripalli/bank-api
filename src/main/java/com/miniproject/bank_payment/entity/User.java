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

    private String phoneNumber;

    private String roles;

    // Getters and Setters
    //construtor
    //allargsconstrutor
}



