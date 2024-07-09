package com.miniproject.bank_payment.controller;


import com.miniproject.bank_payment.entity.Transactions;
import com.miniproject.bank_payment.dto.request.DepositWithdrawRequest;
import com.miniproject.bank_payment.dto.request.TransferRequest;
import com.miniproject.bank_payment.dto.request.UserDTO;
import com.miniproject.bank_payment.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/accounts")
public class AccountController {
         @Autowired
         private AccountService accountService;

    @PostMapping("/create")
    @PreAuthorize("has")
    public String createNewAccount(@RequestBody UserDTO userDTO) {
        return accountService.createNewAccount(userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getPhoneNumber());
    }

         @GetMapping("/{accountId}/balance")
         public Double retrieveAccountBalance(@PathVariable Long accountId) {
             return accountService.retrieveAccountBalance(accountId);
         }

         @PostMapping("/deposit")
         public String depositFunds(@RequestBody DepositWithdrawRequest request) {
             return accountService.depositFunds(request.getAccountId(), request.getAmount());
         }

         @PostMapping("/withdraw")
         public String withdrawFunds(@RequestBody DepositWithdrawRequest request) {
             return accountService.withdrawFunds(request.getAccountId(), request.getAmount());
         }

         @PostMapping("/transfer")
         public String transferFunds(@RequestBody TransferRequest request) {
             return accountService.transferFunds(request.getSourceAccountId(), request.getDestinationAccountId(), request.getAmount());
         }

         @GetMapping("/{accountId}/transactions")
         public List<Transactions> getTransactionHistory(@PathVariable Long accountId) {
             return accountService.getTransactionHistory(accountId);
         }
     }



