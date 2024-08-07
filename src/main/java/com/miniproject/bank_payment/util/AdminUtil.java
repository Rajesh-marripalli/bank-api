package com.miniproject.bank_payment.util;

import com.miniproject.bank_payment.entity.Admin;
import com.miniproject.bank_payment.entity.User;
import com.miniproject.bank_payment.repository.AdminRepo;
import com.miniproject.bank_payment.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class AdminUtil {
    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Autowired
    private AdminRepo adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (adminRepository.findByUsername(adminUsername) == null) {
            Admin adminUser = new Admin();
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRoles("ROLE_ADMIN");
            adminRepository.save(adminUser);
        }
    }
}



