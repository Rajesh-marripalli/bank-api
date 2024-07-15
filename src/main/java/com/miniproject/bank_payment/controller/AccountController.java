package com.miniproject.bank_payment.controller;

import com.miniproject.bank_payment.dto.request.DepositWithdrawRequest;
import com.miniproject.bank_payment.dto.request.TransferRequest;
import com.miniproject.bank_payment.dto.request.UserDTO;
import com.miniproject.bank_payment.dto.response.AccountCreationResponse;
import com.miniproject.bank_payment.dto.response.AccountTransactionResponse;
import com.miniproject.bank_payment.dto.response.TransactionDetail;
import com.miniproject.bank_payment.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountCreationResponse> createNewAccount(@RequestBody UserDTO userDTO) {
        AccountCreationResponse response = accountService.createNewAccount(userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getRoles(), userDTO.getPhoneNumber());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}/balance")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> retrieveAccountBalance(@PathVariable Long accountId) {
        Double balance = accountService.retrieveAccountBalance(accountId);
        Map<String, Object> response = new HashMap<>();
        response.put("accountId", accountId);
        response.put("balance", balance);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> depositFunds(@RequestBody DepositWithdrawRequest request) {
        String result = accountService.depositFunds(request.getAccountId(), request.getAmount());
        Map<String, Object> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> withdrawFunds(@RequestBody DepositWithdrawRequest request) {
        String result = accountService.withdrawFunds(request.getAccountId(), request.getAmount());
        Map<String, Object> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> transferFunds(@RequestBody TransferRequest request) {
        String result = accountService.transferFunds(request.getSourceAccountId(), request.getDestinationAccountId(), request.getAmount());
        Map<String, Object> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}/transactions")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountTransactionResponse> getTransactionHistory(@PathVariable Long accountId) {
        AccountTransactionResponse response = accountService.getTransactionHistory(accountId);
        return ResponseEntity.ok(response);
    }
}
