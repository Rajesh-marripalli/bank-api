package com.miniproject.bank_payment.exceptions;

import com.miniproject.bank_payment.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ApiError> nouserfound()
    {
        ApiError error = new ApiError("no userfound",new Date());
        return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiError> resourceNotFoundException() {
        ApiError errorDetails = new ApiError("resourcenotfound", new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
