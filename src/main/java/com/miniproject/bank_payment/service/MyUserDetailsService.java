package com.miniproject.bank_payment.service;

import com.miniproject.bank_payment.entity.Admin;
import com.miniproject.bank_payment.exceptions.UserNotFoundException;
import com.miniproject.bank_payment.repository.AdminRepo;
import com.miniproject.bank_payment.security.CustomUserDetails;
import com.miniproject.bank_payment.entity.User;
import com.miniproject.bank_payment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByUsername(username);
        if (admin == null) {
            throw new UserNotFoundException("User not found");
        }
        return new CustomUserDetails(admin);
    }
}
