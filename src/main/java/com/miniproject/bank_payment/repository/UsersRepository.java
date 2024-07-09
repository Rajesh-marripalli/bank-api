package com.miniproject.bank_payment.repository;

import com.miniproject.bank_payment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);
}
