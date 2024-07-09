package com.miniproject.bank_payment.repository;

import com.miniproject.bank_payment.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions,Long> {
    List<Transactions> findByAccountAccountId(Long accountId);
}
