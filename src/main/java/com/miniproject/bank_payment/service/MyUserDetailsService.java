package com.miniproject.bank_payment.service;

import com.miniproject.bank_payment.exceptions.UserNotFoundException;
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
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
}
