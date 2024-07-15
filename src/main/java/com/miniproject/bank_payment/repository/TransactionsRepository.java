package com.miniproject.bank_payment.repository;

import com.miniproject.bank_payment.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions,Long> {
    @Query("SELECT t FROM Transactions t WHERE t.account.accountId = :accountId")
    List<Transactions> findByAccountId(@Param("accountId") Long accountId);
}
