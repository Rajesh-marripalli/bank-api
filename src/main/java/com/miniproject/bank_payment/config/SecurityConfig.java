package com.miniproject.bank_payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@Enable
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder) {
        return new UserDetailsService() {
            //TODO configure Authentication
            //TODO Configure the httSecurity
        }
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
       //Todo Jwt filter : intercept and validate JWT Token

    }
}


    //public Pass

}
