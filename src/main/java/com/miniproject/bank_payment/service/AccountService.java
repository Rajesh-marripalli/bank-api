package com.miniproject.bank_payment.service;

import com.miniproject.bank_payment.appconstant.AppConstant;
import com.miniproject.bank_payment.dto.response.AccountCreationResponse;
import com.miniproject.bank_payment.dto.response.AccountTransactionResponse;
import com.miniproject.bank_payment.dto.response.TransactionDetail;
import com.miniproject.bank_payment.entity.Accounts;
import com.miniproject.bank_payment.entity.Transactions;
import com.miniproject.bank_payment.entity.User;
import com.miniproject.bank_payment.exceptions.FieldCannotNullException;
import com.miniproject.bank_payment.exceptions.ResourceNotFound;
import com.miniproject.bank_payment.repository.AccountsRepository;
import com.miniproject.bank_payment.repository.TransactionsRepository;
import com.miniproject.bank_payment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountsRepository accountRepository;
    @Autowired
    private TransactionsRepository transactionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

@Transactional
public AccountCreationResponse createNewAccount(String username, String password, String email, String roles, String phoneNumber) {
    // Validate input fields
    validateInputFields(username, password, email, roles, phoneNumber);
    if (userRepository.existsByEmail(email)) {
        throw new ResourceNotFound("Email address is already registered");
    }
    if (userRepository.existsByPhoneNumber(phoneNumber)) {
        throw new ResourceNotFound("Phone number is already registered");

    }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRoles(roles);
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);

        Accounts account = new Accounts();
        account.setUser(user);
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(0.0);
        accountRepository.save(account);

        // Return success response
        return new AccountCreationResponse(AppConstant.ACCOUNT_CREATION_SUCCESS, account.getAccountNumber());

}

    private void validateInputFields(String username, String password, String email, String roles, String phoneNumber) {
        if (username == null || username.isEmpty()) {
            throw new FieldCannotNullException("Username cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new FieldCannotNullException("Password cannot be null or empty");
        }
        if (email == null || email.isEmpty()) {
            throw new FieldCannotNullException("Email cannot be null or empty");
        }
        if (roles == null || roles.isEmpty()) {
            throw new FieldCannotNullException("Roles cannot be null or empty");
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new FieldCannotNullException("Phone number cannot be null or empty");
        }
    }
    public Double retrieveAccountBalance(Long accountId) {
        Optional<Accounts> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            return account.get().getBalance();
        } else {
            throw new ResourceNotFound("Account id is not found " + accountId);
        }
    }

    public String depositFunds(Long accountId, Double amount) {
        if (amount == null || amount <= 0) {
            return AppConstant.INVALID_TRANSFER_AMOUNT;
        }
        Optional<Accounts> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isPresent()) {
            Accounts account = accountOpt.get();
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);

            Transactions transaction = new Transactions();
            transaction.setAccount(account);
            transaction.setTransactionType("Deposit");
            transaction.setAmount(amount);
            transaction.setTransactionDate(new java.util.Date());
            transactionRepository.save(transaction);

            return AppConstant.FUNDS_DEPOSIT_SUCCESS;
        } else {
            return AppConstant.ACCOUNT_NOT_FOUND;
        }
    }

    public String withdrawFunds(Long accountId, Double amount) {
        if (amount == null || amount <= 0) {
            return AppConstant.INVALID_AMOUNT;
        }
        Optional<Accounts> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isPresent()) {
            Accounts account = accountOpt.get();
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                accountRepository.save(account);

                Transactions transaction = new Transactions();
                transaction.setAccount(account);
                transaction.setTransactionType("Withdraw");
                transaction.setAmount(amount);
                transaction.setTransactionDate(new java.util.Date());
                transactionRepository.save(transaction);

                return AppConstant.FUNDS_WITHDRAW_SUCCESS;
            } else {
                return AppConstant.INSUFFICIENT_BALANCE;
            }
        } else {
            return AppConstant.ACCOUNT_NOT_FOUND;
        }
    }

    public String transferFunds(Long sourceAccountId, Long destinationAccountId, Double amount) {
        if (amount == null || amount <= 0) {
            return AppConstant.INVALID_TRANSFER_AMOUNT;
        }
        Optional<Accounts> sourceAccountOpt = accountRepository.findById(sourceAccountId);
        Optional<Accounts> destinationAccountOpt = accountRepository.findById(destinationAccountId);
         if (sourceAccountOpt.isEmpty()) {
            return AppConstant.INVALID_SOURCE_ACCOUNT;
        } else if (destinationAccountOpt.isEmpty()) {
            return AppConstant.INVALID_DESTINATION_ACCOUNT;
        }
        if (sourceAccountOpt.isPresent() && destinationAccountOpt.isPresent()) {

            Accounts sourceAccount = sourceAccountOpt.get();
            Accounts destinationAccount = destinationAccountOpt.get();

            if (sourceAccount.getBalance() >= amount) {
                sourceAccount.setBalance(sourceAccount.getBalance() - amount);

                destinationAccount.setBalance(destinationAccount.getBalance() + amount);
                accountRepository.save(sourceAccount);
                accountRepository.save(destinationAccount);

                Transactions transaction = new Transactions();
                transaction.setAccount(sourceAccount);
                transaction.setTransactionType("debit");
                transaction.setAmount(amount);
                transaction.setTransactionDate(new java.util.Date());
                transactionRepository.save(transaction);

                transaction = new Transactions();
                transaction.setAccount(destinationAccount);
                transaction.setTransactionType("credit");
                transaction.setAmount(amount);
                transaction.setTransactionDate(new java.util.Date());
                transactionRepository.save(transaction);

                return AppConstant.FUNDS_TRANSFER_SUCCESS;
            } else {
                return AppConstant.INSUFFICIENT_BALANCE;
            }
        } else {
            return AppConstant.SOURCE_DESTINATION_ACCOUNT_NOT_FOUND;
        }
    }
    @Transactional(readOnly = true)
    public AccountTransactionResponse getTransactionHistory(Long accountId) {

        Accounts account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFound("Account not found with ID " + accountId));

            List<Transactions> transactions = transactionRepository.findByAccountId(accountId);
            List<TransactionDetail> transactionDetails = transactions.stream()
                    .map(transaction -> new TransactionDetail(
                            transaction.getTransactionType(),
                            transaction.getAmount(),
                            transaction.getTransactionDate()
                    ))
                    .collect(Collectors.toList());
            return new AccountTransactionResponse(account.getAccountNumber(), account.getBalance(), transactionDetails);
        }


    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
}
