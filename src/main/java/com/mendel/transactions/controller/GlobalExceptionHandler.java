package com.mendel.transactions.controller;


import com.mendel.transactions.dto.StatusResponse;
import com.mendel.transactions.exception.ParentTransactionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParentTransactionNotFoundException.class)
    public ResponseEntity<StatusResponse> handleIllegalArgument(ParentTransactionNotFoundException ex) {
        return ResponseEntity.badRequest().body(new StatusResponse("error", ex.getMessage()));
    }
}