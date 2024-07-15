package com.miniproject.bank_payment.service;

import com.miniproject.bank_payment.entity.Accounts;
import com.miniproject.bank_payment.entity.Transactions;
import com.miniproject.bank_payment.repository.AccountsRepository;
import com.miniproject.bank_payment.repository.TransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountsRepository accountRepository;

    @Mock
    private TransactionsRepository transactionsRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAccountBalance() {
        Accounts account = new Accounts();
        account.setAccountId(1L);
        account.setBalance(1000.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Double balance = accountService.retrieveAccountBalance(1L);

        assertNotNull(balance);
        assertEquals(1000.0, balance);
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testDepositFunds() {
        Accounts account = new Accounts();
        account.setAccountId(1L);
        account.setBalance(1000.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Accounts.class))).thenReturn(account);
        when(transactionsRepository.save(any(Transactions.class))).thenReturn(new Transactions());

        String response = accountService.depositFunds(1L, 500.0);
        assertEquals(1500.0, account.getBalance());
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(account);
        verify(transactionsRepository, times(1)).save(any(Transactions.class));
    }

    @Test
    void testWithdrawFunds() {
        Accounts account = new Accounts();
        account.setAccountId(1L);
        account.setBalance(1000.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Accounts.class))).thenReturn(account);
        when(transactionsRepository.save(any(Transactions.class))).thenReturn(new Transactions());

        String response = accountService.withdrawFunds(1L, 500.0);

        System.out.println("Actual response (withdraw): '" + response + "'");
        assertEquals("Funds withdrawn successfully.", response);
        assertEquals(500.0, account.getBalance());
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(account);
        verify(transactionsRepository, times(1)).save(any(Transactions.class));
    }
}
