package com.miniproject.bank_payment.repository;

import com.miniproject.bank_payment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {


    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
