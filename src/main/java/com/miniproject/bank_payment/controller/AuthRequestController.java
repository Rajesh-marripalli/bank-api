package com.miniproject.bank_payment.controller;

import com.miniproject.bank_payment.dto.request.AuthRequest;
import com.miniproject.bank_payment.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRequestController {
    @Autowired
   private  AuthRequest authRequest;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthRequest authRequest)
    {
        return jwtService.generateToken(authRequest.getUsername());

        //TODO Handle and generate JWT Tokens
    }

}
