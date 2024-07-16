package com.miniproject.bank_payment.repository;

import com.miniproject.bank_payment.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin,Integer > {

    Admin findByUsername(String adminUsername);
}
