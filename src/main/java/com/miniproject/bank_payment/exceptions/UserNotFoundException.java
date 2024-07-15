package com.miniproject.bank_payment.exceptions;

public class UserNotFoundException extends RuntimeException{
 public UserNotFoundException(String msg)
 {
     super(msg);
 }
}
