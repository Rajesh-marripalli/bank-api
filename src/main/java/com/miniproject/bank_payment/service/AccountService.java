package com.miniproject.bank_payment.service;


import com.miniproject.bank_payment.Appconstent.AppConstant;
import com.miniproject.bank_payment.entity.Accounts;
import com.miniproject.bank_payment.entity.Transactions;
import com.miniproject.bank_payment.entity.User;
import com.miniproject.bank_payment.repository.AccountsRepository;
import com.miniproject.bank_payment.repository.TransactionsRepository;
import com.miniproject.bank_payment.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class AccountService {
    // AccountService.java
        @Autowired
        private UsersRepository userRepository;
        @Autowired
        private AccountsRepository accountRepository;
        @Autowired
        private TransactionsRepository transactionRepository;

        public String createNewAccount(String username, String password, String email, Long phoneNumber) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber);
            userRepository.save(user);

            Accounts account = new Accounts();
            account.setUser(user);//here it will be link to the user account
            account.setAccountNumber(generateAccountNumber());//i genererated random bank account
            account.setBalance(0.0);
            accountRepository.save(account);

            return AppConstant.ACCOUNT_CREATION_SUCCESS + account.getAccountNumber();
        }

        public Double retrieveAccountBalance(Long accountId) {
            Optional<Accounts> account = accountRepository.findById(accountId);
            return account.map(Accounts::getBalance).orElse(null);
        }

        public String depositFunds(Long accountId, Double amount) {
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
            Optional<Accounts> sourceAccountOpt = accountRepository.findById(sourceAccountId);
            Optional<Accounts> destinationAccountOpt = accountRepository.findById(destinationAccountId);
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
                    transaction.setTransactionType("Out");
                    transaction.setAmount(amount);
                    transaction.setTransactionDate(new java.util.Date());
                    transactionRepository.save(transaction);

                    transaction = new Transactions();
                    transaction.setAccount(destinationAccount);
                    transaction.setTransactionType("In");
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

        public List<Transactions> getTransactionHistory(Long accountId) {
            return transactionRepository.findByAccountAccountId(accountId);
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




